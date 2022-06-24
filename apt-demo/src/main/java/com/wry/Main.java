package com.wry;

import com.wry.annotation.ApiAnnotation;

import java.util.ServiceLoader;

@ApiAnnotation(msg = "world")
public class Main {

    public static void main(String[] args) {
        ServiceLoader<ApiInterface> loader = ServiceLoader.load(ApiInterface.class);
        for (ApiInterface myServiceLoader : loader) {
            myServiceLoader.print();
            System.out.println(myServiceLoader.getClass().getSimpleName());
        }
    }

}
