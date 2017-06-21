package com.xsr.demo.my_jeesite.com.xsr.jeesite.codegen;

/**
 * Created by xs on 2017/6/16.
 */
public class Main {
    public static void main(String[] args){
        CodeGen codeGen = new CodeGen();
        try {
            codeGen.genCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String hello(){
        return "hello world!";
    }
}
