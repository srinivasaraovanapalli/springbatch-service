package com.springbatch.net.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.net.entity.Employee;
import com.springbatch.net.repository.EmployeeRepository;

@Configuration
@EnableBatchProcessing
public class CsvBatchConfig {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	//Create Reader
	@Bean
	public FlatFileItemReader<Employee> employeeReader(){
		
		FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("src/main/resources/employee.csv"));
		itemReader.setLinesToSkip(1);
		itemReader.setName("csv-reader");
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}
	
	private LineMapper<Employee> lineMapper(){
		DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		delimitedLineTokenizer.setNames("id","name","mobile","salary","dept");
		
		BeanWrapperFieldSetMapper<Employee> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(Employee.class);
		
		lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		lineMapper.setLineTokenizer(delimitedLineTokenizer);
		return lineMapper;
	}
	
	//Create Processor
	@Bean
	public EmployeeProcessor employeeProcessor() {
		return new EmployeeProcessor();
	}
	
	//Create Writer
	@Bean
	public RepositoryItemWriter<Employee> employeeWriter() {
		
		RepositoryItemWriter<Employee> repositoryItemWriter = new RepositoryItemWriter<>();
		repositoryItemWriter.setRepository(employeeRepository);
		repositoryItemWriter.setMethodName("save");
		return repositoryItemWriter;
	}
	
	//Create Step
	@Bean
	public Step step() {
		return (Step) stepBuilderFactory.get("step-1").<Employee, Employee>chunk(10)
				.reader(employeeReader())
				.processor(employeeProcessor())
				.writer(employeeWriter()).build();
	}
	
	//Create Job
	@Bean
	public Job job() {
		return jobBuilderFactory.get("employee-job").flow(step()).end().build();
	}
}
