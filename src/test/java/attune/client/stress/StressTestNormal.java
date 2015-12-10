package attune.client.stress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class StressTestNormal {
	private static final Logger logger =
			LoggerFactory.getLogger(StressTestNormal.class);
	private static TypeReference<HashMap<String, List<String>>> typ =
			new TypeReference<HashMap<String, List<String>>>(){};
	private static ObjectMapper mapper = new ObjectMapper();

	private static class TestConfig {
		public String serverUrl;
		public String authToken;
		public String application;
		public int concurrentCustomers;
		public double avgNumCalls;
		public double avgCallGap;
		public int duration;
		public int numGroups;
		public String entityType;
		public String customerFile;
		public String entityFile;
	}

	private static class TestThread implements Runnable {
		private TestConfig config;
		private Date endTime;
		public int totalCalls, successfulCalls;
		public double callTime;
		private List<String> views;
		private List<List<String>> entities;
		private List<String> customers;
		private Random random;
		private AttuneClient client;
		private boolean showResponse;

		public TestThread(AttuneClient client, TestConfig config,
				List<String> views, List<List<String>> entities,
				List<String> customers, boolean showResponse) {
			this.client = client;
			this.config = config;
			this.views = views;
			this.entities = entities;
			this.customers = customers;
			this.random = new Random();
			this.showResponse = showResponse;
		}

		private List<RankingParams> getParams(String aId, String cId, int num) {
			List<RankingParams> retVal = new ArrayList<RankingParams>();
			for (int i = 0; i < num; i++)
				retVal.add(getParams(aId, cId));
			return retVal;
		}
		
		private RankingParams getParams(String aId, String cId) {
			RankingParams retVal = new RankingParams();
			retVal.setAnonymous(aId);
			retVal.setCustomer(cId);
			int index = random.nextInt(views.size());
			retVal.setView(views.get(index));
			retVal.setIds(entities.get(index));
			retVal.setApplication(config.application);
			retVal.setEntityType(config.entityType);
			return retVal;
		}

		private boolean callRank(String aId, String cId) {
			List<RankingParams> params = getParams(aId, cId, config.numGroups);
			long start = System.currentTimeMillis();
			RankedEntities entry = null;
			//List<RankedEntities> batchRankings;
			try {
				//batchRankings = client.batchGetRankings(params,
				//		config.authToken);
				entry = client.getRankings(params.get(0), config.authToken);
				if (showResponse) logger.debug(mapper.writeValueAsString(entry));
			} catch (ApiException | IOException e) {
				if (showResponse) logger.error(e.getMessage());
				return false;
			}
			long end = System.currentTimeMillis();
			callTime += (end - start);
			
			//for (RankedEntities entry : batchRankings) {
			//	if (entry.getStatus() >= 400) return false;
			//}
			if ((entry == null) || (entry.getStatus() != null)) return false;
			return true;
		}

		@Override
		public void run() {
			initialize();
			Random random = new Random();
			double alpha = 1 - 1 / config.avgNumCalls;

			while (endTime.after(new Date())) {
				while (endTime.after(new Date())) {
					double gap = random.nextDouble() * config.avgCallGap * 2000.0;
					try {
						Thread.sleep(Math.round(gap));
					} catch (Exception ex) {
						return;
					}

					totalCalls ++;
					try {
						if (callRank(random.nextInt(10000000) + "",
								getRandomCustomer(random)))
							successfulCalls ++;
					} catch (Exception ex) {
						if (showResponse) logger.error("Error : ", ex);
					}

					if (random.nextDouble() > alpha)
						break;
				}
			}
		}

		private String getRandomCustomer(Random random) {
			int k = random.nextInt(customers.size());
			return customers.get(k);
		}

		private void initialize() {
			// initialize context
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.SECOND, config.duration);
			endTime = cal.getTime();

			totalCalls = 0;
			successfulCalls = 0;
			callTime = 0;
		}
	}
	
	public static void runTest(String configFile, boolean showResponse)
	    throws Exception {
		TestConfig conf =  mapper.readValue(new File(configFile), TestConfig.class);

		logger.debug(mapper.writeValueAsString(conf));
		
		AttuneConfigurable configurable =
				new AttuneConfigurable(conf.serverUrl, 1000, 200, 10.0, 5.0);
		
		AttuneClient client = AttuneClient.buildWith(configurable);

		List<TestThread> tests = new ArrayList<TestThread>();
		List<Thread> threads = new ArrayList<Thread>();
		List<List<String>> entities = new ArrayList<List<String>>();
		List<String> views = new ArrayList<String>();
		readEntities(conf.entityFile, views, entities);
		List<String> customers = readCustomers(conf.customerFile);

		for (int index = 0; index < conf.concurrentCustomers; index ++) {
			TestThread test = new TestThread(client, conf, views, entities,
					customers, showResponse);
			Thread thread = new Thread(test);
			thread.start();
			tests.add(test);
			threads.add(thread);
		}

		for (Thread thread : threads)
			thread.join();

		int tCalls = 0, sCalls = 0;
		double tCTime = 0;
		for (TestThread test : tests) {
			tCalls += test.totalCalls;
			sCalls += test.successfulCalls;
			tCTime += test.callTime;
		}
		logger.error("Total calls: " + tCalls + ", successful calls: " + sCalls
				+ ", average call time: " + tCTime / tCalls);
	}
	
	private static void readEntities(String filename, List<String> views,
			List<List<String>> entities) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		while (true) {
			String entity = br.readLine();
			if (entity == null) break;
			Map<String, List<String>> info = mapper.readValue(entity, typ);
			for (Map.Entry<String, List<String>> entry : info.entrySet()) {
				views.add(entry.getKey());
				entities.add(entry.getValue());
			}
		}
		br.close();
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
	  boolean showResponse = false;
	  logger.info("starting with config file {}", argv[0]);
	  if (argv.length > 1) {
	    showResponse = (Integer.parseInt(argv[1]) == 0);
	  }
		runTest(argv[0], showResponse);
	}
}