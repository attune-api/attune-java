package attune.client.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import attune.client.ApiException;
import attune.client.AttuneClient;
import attune.client.AttuneConfigurable;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

public class StressTestOKLMobileSpecial {
	private static final Logger logger =
			LoggerFactory.getLogger(StressTestOKLMobileSpecial.class);
	private static ObjectMapper mapper = new ObjectMapper();

	private static class TestConfig {
		public String serverUrl;
		public String authToken;
		public String application;
		public int numCustomers;
		public int numSales;
		public int maxConns;
		public int maxConnsPerSite;
		public int sleepTime;
		public String customerFile;
		public String entityFile;
	}
	
	private static class CallThread implements Runnable {
		public List<Boolean> success = new ArrayList<Boolean>();
		public List<Long> callTimes = new ArrayList<Long>();
		
		private Random random = new Random();
		private TestConfig conf;
		private List<String> customers;
		private String view;
		private AttuneClient client;
		private boolean showResponse;
		
		public CallThread(TestConfig conf, AttuneClient client, String view,
		    List<String> customers, boolean showResponse) {
			this.conf = conf;
			this.customers = customers;
			this.view = view;
			this.client = client;
			this.showResponse = showResponse;
		}

		private RankingParams getParams() {
			RankingParams retVal = new RankingParams();
			int k = random.nextInt(customers.size());
			String cid = customers.get(k);
			String aid = random.nextInt(10000000) + "";
			
			retVal.setAnonymous(aid);
			retVal.setCustomer(cid);
			retVal.setView("sales/" + view);
			retVal.setIds(new ArrayList<String>());
			retVal.setApplication(conf.application);
			retVal.setEntityType("products");
			retVal.setEntitySource("scope");
			
			List<String> scopes = new ArrayList<String>();
		  scopes.add("sale=" + view);
		  retVal.setScope(scopes);
			return retVal;
		}

		@Override
		public void run() {
			for (int i = 0; i < conf.numCustomers; i++) {
				RankingParams params = getParams();
				long start = System.currentTimeMillis();
				RankedEntities ranking;
				try {
					ranking = client.getRankings(params, conf.authToken);
					long end = System.currentTimeMillis();
					callTimes.add(end - start);
					success.add(ranking != null);
					if (showResponse) logger.debug(mapper.writeValueAsString(ranking));
					
				} catch (ApiException | IOException e) {
					success.add(false);
					callTimes.add(0l);
					if (showResponse) logger.error(e.getMessage());
				}
				try {
					Thread.sleep(conf.sleepTime);
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
			}
		}
	}
	
	public static void runTest(String configFile, boolean showResponse)
	    throws Exception {
		TestConfig conf =  mapper.readValue(new File(configFile), TestConfig.class);

		logger.debug(mapper.writeValueAsString(conf));
		
		AttuneConfigurable configurable = new AttuneConfigurable(conf.serverUrl,
				conf.maxConns, conf.maxConnsPerSite, 5.0, 10.0);
		
		AttuneClient client = AttuneClient.getInstance(configurable);

		List<CallThread> tests = new ArrayList<CallThread>();
		List<Thread> threads = new ArrayList<Thread>();
		List<String> views = readCustomers(conf.entityFile);
		List<String> customers = readCustomers(conf.customerFile);
		
		logger.debug("Start");
		for (int i = 0; i < conf.numSales; i++) {
		  int viewIndex = i % views.size();
			CallThread test = new CallThread(conf, client, views.get(viewIndex),
			    customers, showResponse);
			Thread thread = new Thread(test);
			thread.start();
			tests.add(test);
			threads.add(thread);
		}
		
		for (Thread thread : threads)
			thread.join();
		
		logger.debug("Complete");

		List<Long> callTimes = new ArrayList<Long>();
		int sCalls = 0, tCalls = 0;
		double sTime = 0;
		for (CallThread test : tests) {
			List<Long> cTimes = test.callTimes;
			List<Boolean> success = test.success;
			tCalls += success.size();
			for (int i = 0; i < success.size(); i++) {
				if (success.get(i)) {
					callTimes.add(cTimes.get(i));
					sTime += cTimes.get(i);
					sCalls ++;
				}
			}
		}
		
		double minTime = 0;
		double medianTime = 0;
		double tail90 = 0;
		double tail95 = 0;
		double tail99 = 0;
		double maxTime = 0;
		if (sCalls > 0) {
			sTime /= sCalls;
			Collections.sort(callTimes);
			minTime = callTimes.get(0);
			medianTime = callTimes.get(sCalls/2);
			maxTime = callTimes.get(sCalls - 1);
			tail90 = callTimes.get(sCalls - 1 - sCalls / 10);
			tail95 = callTimes.get(sCalls - 1 - sCalls / 20);
			tail99 = callTimes.get(sCalls - 1 - sCalls / 100);
		}
		
		logger.debug("Total calls: " + tCalls + ", successful calls: " + sCalls
				+ ", call time: " + minTime + "-" + medianTime + "-" + sTime
				+ "-" + tail90 + "-" + tail95 + "-" + tail99 + "-" + maxTime);
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
    if (argv.length > 1) {
      showResponse = (Integer.parseInt(argv[1]) == 0);
    }
		runTest(argv[0], showResponse);
	}
}
