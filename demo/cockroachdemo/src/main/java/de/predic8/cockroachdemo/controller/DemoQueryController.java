package de.predic8.cockroachdemo.controller;

import de.predic8.cockroachdemo.service.DemoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class DemoQueryController {

    @Autowired
    DemoQueryService demoQueryService;

    @GetMapping("/query1")
    public String query1(@RequestParam("customerId") int customerId) throws Exception {
        return time(() -> demoQueryService.runQuery1(customerId));
    }

    @GetMapping("/query2")
    public String query2(@RequestParam("customerId") int customerId) throws Exception {
        return time(() -> demoQueryService.runQuery2(customerId));
    }

    private String time(Callable<Integer> it) throws Exception {
        StringBuilder sb = new StringBuilder();

        long now = System.currentTimeMillis();
        int result = it.call();
        long duration = System.currentTimeMillis() - now;

        sb.append("result: ");
        sb.append(result);
        sb.append("\nduration: ");
        sb.append(duration);
        sb.append(" ms");

        return sb.toString();
    }

}
