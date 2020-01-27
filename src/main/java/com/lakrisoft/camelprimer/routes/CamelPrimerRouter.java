package com.lakrisoft.camelprimer.routes;

import java.lang.management.ManagementFactory;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.sun.management.OperatingSystemMXBean;

/**
 * A simple Camel route that triggers from a timer.
 */
@Component
public class CamelPrimerRouter extends RouteBuilder
{
  @Override
  public void configure() throws Exception
  {
    from("timer:hello?period=1000")
        .setBody((e) -> String.valueOf(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getProcessCpuLoad()))
        .to("seda:my-queue");
    
    from("seda:my-queue")
        .log("Process CPU load was ${body}");
  }
}
