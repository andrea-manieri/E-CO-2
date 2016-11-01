/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.edisonproject.common.tools;

import eu.edisonproject.common.mappers.StemmerMapper;
import eu.edisonproject.common.reducers.CleanReducer;
import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 *
 * @author S. Koulouzis
 */
public class Stemm extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {
    Configuration jobconf = getConf();

    Job job = new Job(jobconf);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    job.setJarByClass(Stemm.class);
    job.setMapperClass(StemmerMapper.class);

    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
    Path inPath = new Path(args[0]);
    FileInputFormat.setInputPaths(job, inPath);

    job.setReducerClass(CleanReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);

    FileSystem fs = FileSystem.get(getConf());
    FileStatus[] statusList = fs.listStatus(inPath);
    for (FileStatus fileStatus : statusList) {
      String name = FilenameUtils.removeExtension(fileStatus.getPath().getName());
      MultipleOutputs.addNamedOutput(job, name, TextOutputFormat.class,
              Text.class, Text.class);
    }

    Path outPath = new Path(args[1]);
    FileOutputFormat.setOutputPath(job, outPath);

    return (job.waitForCompletion(true) ? 0 : 1);

  }

}