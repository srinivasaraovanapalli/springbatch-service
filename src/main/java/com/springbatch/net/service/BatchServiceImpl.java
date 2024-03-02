package com.springbatch.net.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * The application which will process bulk of records 
 * is called batch application
 * 
 * CSV -> DB
 * 1.JobLauncher - is execute the job
 * 2.Job - represent steps -> executing single step or multiple steps
 * 3.Step - represents task -> reader, writer, processor is called one step
 * 4.ItemReader - reading data
 * 5.ItemProcessor - processing data
 * 6.ItemWriter - writing data
 * 7.JobRepository - maintaining all batch information
 * Job Repository -> Job Launcher -> Job -> Step -> Process Reader/Process/Writer
 */
@Service
public class BatchServiceImpl implements BatchService {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;

	@Scheduled(cron = "0 * * * * *")
	public void loadCsvToDb() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		System.out.print("start job");
		JobParameters jobParameters = new JobParametersBuilder().addLong("Start-At", System.currentTimeMillis()).toJobParameters();
		jobLauncher.run(job, jobParameters);
	}
}
