package attune.client.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import attune.client.ApiException;
import attune.client.AttuneClient;
import attune.client.AttuneConfigurable;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

public class StressTestOKLMobileSpecial {
	private static final Logger logger =
			LoggerFactory.getLogger(StressTestOKLMobileSpecial.class);
	private static TypeReference<HashMap<String, List<String>>> typ =
			new TypeReference<HashMap<String, List<String>>>(){};
	private static ObjectMapper mapper = new ObjectMapper();

	private static class TestConfig {
		public String serverUrl;
		public String authToken;
		public String application;
		public double numCustomers;
		public int numSales;
		public int maxConns;
		public int maxConnsPerSite;
		public int numSeconds;
		public String customerFile;
		public String entityFile;
	}
	
	private static class CallThread implements Runnable {
		public boolean success = false;
		public long callTime = 0;
		
		private String aid;
		private String cid;
		private String view;
		private String app;
		private List<String> items;
		private AttuneClient client;
		private String token;
		
		public CallThread(String aid, String cid, String view, String app,
				List<String> items, AttuneClient client, String token) {
			this.aid = aid;
			this.cid = cid;
			this.view = view;
			this.app = app;
			this.items = items;
			this.client = client;
			this.token = token;
		}

		private RankingParams getParams() {
			RankingParams retVal = new RankingParams();
			retVal.setAnonymous(aid);
			retVal.setCustomer(cid);
			retVal.setView(view);
			retVal.setIds(items);
			retVal.setApplication(app);
			retVal.setEntityType("products");
			return retVal;
		}

		@Override
		public void run() {
			RankingParams params = getParams();
			long start = System.currentTimeMillis();
			RankedEntities ranking;
			try {
				ranking = client.getRankings(params, token);
				long end = System.currentTimeMillis();
				callTime = (end - start);
				success = (ranking != null);
			} catch (ApiException e) {
				logger.error(e.getMessage());
			}
		}
	}

	private static class TestThread implements Runnable {
		public boolean success = false;
		public double avgCallTime;
		public double totalTime;
		
		private TestConfig config;
		private List<String> views;
		private List<List<String>> entities;
		private List<String> customers;
		private Random random;
		private AttuneClient client;

		public TestThread(AttuneClient client, TestConfig config,
				List<String> views, List<List<String>> entities,
				List<String> customers) {
			this.client = client;
			this.config = config;
			this.views = views;
			this.entities = entities;
			this.customers = customers;
			this.random = new Random();
		}
		
		@Override
		public void run() {
			System.out.println("OK");
			List<Thread> threads = new ArrayList<Thread>();
			List<CallThread> calls = new ArrayList<CallThread>();
			
			String cid = getRandomCustomer(random);
			String aid = random.nextInt(10000000) + "";
			
			long start = System.currentTimeMillis();
			for (int i = 0; i < views.size(); i++) {
				CallThread call = new CallThread(aid, cid, views.get(i),
						config.application, entities.get(i), client,
						config.authToken);
				Thread thread = new Thread(call);
				thread.start();
				calls.add(call);
				threads.add(thread);
			}
			
			for (Thread thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			long end = System.currentTimeMillis();
			totalTime = (end - start);
			
			success = true;
			for (CallThread call : calls) {
				avgCallTime += call.callTime;
				if (!call.success) success = false;
			}
			if (views.size() > 0)
				avgCallTime /= views.size();
		}
			
		private String getRandomCustomer(Random random) {
			int k = random.nextInt(customers.size());
			return customers.get(k);
		}
	}
	
	public static void runTest(String configFile) throws IOException,
			InterruptedException {
		TestConfig conf =  mapper.readValue(new File(configFile), TestConfig.class);

		logger.debug(mapper.writeValueAsString(conf));
		
		AttuneConfigurable configurable = new AttuneConfigurable(conf.serverUrl,
				conf.maxConns, conf.maxConnsPerSite);
		configurable.updateConnectionTimeout(10.0);
		configurable.updateReadTimeout(5.0);
		
		AttuneClient client = AttuneClient.getInstance(configurable);

		List<TestThread> tests = new ArrayList<TestThread>();
		List<Thread> threads = new ArrayList<Thread>();
		List<List<String>> entities = new ArrayList<List<String>>();
		List<String> views = new ArrayList<String>();
		readEntities(conf.entityFile, views, entities, conf.numSales);
		List<String> customers = readCustomers(conf.customerFile);
		
		Random random = new Random();
		int totalMS = 0;
		int maxMS = conf.numSeconds * 1000;
		int maxSleepTime = (int) (2000.0 / conf.numCustomers);
		
		while (totalMS < maxMS) {
			TestThread test = new TestThread(client, conf, views, entities,
					customers);
			Thread thread = new Thread(test);
			thread.start();
			tests.add(test);
			threads.add(thread);
			
			int wait = random.nextInt(maxSleepTime);
			Thread.sleep(wait);
			totalMS += wait;
		}

		for (Thread thread : threads)
			thread.join();

		int tCalls = 0, sCalls = 0;
		double tCTime1 = 0, tCTime2 = 0;
		for (TestThread test : tests) {
			tCalls ++;
			if (test.success) {
				sCalls ++;
				tCTime1 += test.avgCallTime;
				tCTime2 += test.totalTime;
			}
		}
		
		if (sCalls > 0) {
			tCTime1 /= sCalls;
			tCTime2 /= sCalls;
		}
		
		logger.debug("Total calls: " + tCalls + ", successful calls: " + sCalls
				+ ", average call time: " + tCTime1 + ", " + tCTime2);
	}
	
	private static void readEntities(String filename, List<String> views,
			List<List<String>> entities, int numSales) throws IOException {
		List<String> tViews = new ArrayList<String>();
		List<List<String>> tEntities = new ArrayList<List<String>>();
		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		while (true) {
			String entity = br.readLine();
			if (entity == null) break;
			Map<String, List<String>> info = mapper.readValue(entity, typ);
			for (Map.Entry<String, List<String>> entry : info.entrySet()) {
				tViews.add(entry.getKey());
				tEntities.add(entry.getValue());
			}
		}
		br.close();
		
		if (tViews.size() == 0) return;
		
		Random rand = new Random();
		for (int i = 0; i < numSales; i++) {
			int index = rand.nextInt(tViews.size());
			views.add(tViews.get(index));
			entities.add(tEntities.get(index));
		}
	}
	
	private static List<String> readCustomers(String filename) throws IOException {
		List<String> retVal = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		while (true) {
			String entity = br.readLine();
			if (entity == null) break;
			retVal.add(entity);
		}
		br.close();
		return retVal;
	}

	public static void main(String [] argv) throws Exception {
		runTest(argv[0]);
	}
}
