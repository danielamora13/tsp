package com.calculateTsp.tsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tsp")
public class Controller {
    @Autowired
    private Service service;

    @PostMapping("/grafo")
    public String resolveTsp(@Validated @RequestBody double[][] grafo) {
        System.out.println(grafo);
        return service.busqueda(grafo).toString();
    }

    @PostMapping("/file")
    public String resolveTspFile(@RequestParam String file) {
        String result;
        long startTime = System.currentTimeMillis();
        result = service.busquedaFile(file).toString();
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Tiempo de ejecución: " + endTime);

        return result;
    }
}
