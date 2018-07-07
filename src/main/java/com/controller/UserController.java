package com.controller;

import com.alibaba.fastjson.JSON;
import com.model.User;
import com.service.IUserService;
import com.util.FuncEnum;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: apple
 * @Date: 2018/7/2 21:18
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/test/{id}")
    public @ResponseBody
    String test(@PathVariable Long id) {
        User user = userService.test(id);
        return JSON.toJSONString(user);
    }

    /**
     * 下面所有代码都是练习题目的demo，直接启动main方法生成结果文件
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        //工具集
        Element body = getElement();
        String[] textArr = body.text().split(" ");
        //文本集
        Element element1 = getElement1();
        String[] resArr = element1.text().split("。");

        //字符顺序的list
        List<String> ls = new ArrayList<>();
        for(int i=1;i<textArr.length;i = i+2){
            ls.add(textArr[i]);
        }
//        System.out.println(ls.size());
        ls.sort((String o1, String o2) -> o1.compareTo(o2));
        //字符倒序的list
        List<String> ls2 = new ArrayList<>();
        ls2.addAll(ls);
        ls2.sort((String o1, String o2) -> o2.compareTo(o1));
        for (int i = 0; i < resArr.length; i++) {
            String res = resArr[i];
            for (FuncEnum e:FuncEnum.values()){
                if(res.contains(e.getName())){
                    switch (e.getIndex()){
                        case 1://自然排序
                            String s = nature(res, i, textArr);
                            resArr[i] = s;
                            break;
                        case 2://索引排序
                            String w = indexOrder(res, i, textArr);
                            resArr[i] = w;
                            break;
                        case 3://字符排序
                            String qq = CharOrder(res, i, ls,true);
                            resArr[i] = qq;
                            break;
                        case 4://字符倒序
                            String b = CharOrder(res, i, ls2,false);
                            resArr[i] = b;
                            break;
                    }
                }

            }
            resArr[i] = resArr[i]+"。";
        }
        write(resArr);
        System.out.println("文件写入成功！！！");
        //read();

    }

    /**
     * sdxl_prop.txt的内容
     * @return
     * @throws IOException
     */
    private static Element getElement() throws IOException {
        Connection connect = Jsoup.connect("http://qfc.qunar.com/homework/sdxl_prop.txt");
        connect.maxBodySize(0);
        //解析Url获取Document对象
        Document document = connect.get();
        //获取网页源码文本内容
        //System.out.println(document.toString());

        //        StringBuffer sb = new StringBuffer();
//        int i = body.text().length();
//        for (int j = 0; j < i; j++) {
//            if((body.text().charAt(j)+"").equals(" ") && j%2 != 0){
//                for (int k = 0; k < j; k++) {
//                    sb.append(body.text().charAt(k)+"");
//                }
//                sb.append(",");
//            }
//        }
        return document.body();
    }

    /**
     * sdxl_template.txt的内容
     * @return
     * @throws IOException
     */
    private static Element getElement1() throws IOException {
        Connection connect = Jsoup.connect("http://qfc.qunar.com/homework/sdxl_template.txt");
        connect.maxBodySize(0);
        //解析Url获取Document对象
        Document document = connect.get();
        return document.body();
    }

    /**
     * 最终结果写到当前工程所在目录下
     * @param s
     * @throws IOException
     */
    private static void write(String[] s) throws IOException {
        File f = new File("./a.txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (f.exists() == true) {
              f.delete();
            }
            f.createNewFile();
            fw = new FileWriter(f, true);
            bw = new BufferedWriter(fw);
            for (String text : s) {
                bw.write(text);
                bw.write("\r\n");
            }
        }catch (Exception e){

        }finally {
        	  bw.close();
        	  fw.close();
        }

    }
    @Deprecated
    private static void read(){
        File f = new File("./a.txt");
        try{
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            while(str!=null){
                System.out.println(str);
                //System.out.println("===");
                str = br.readLine();
            }
                br.close();
                fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *自然排序
     * @param text     结果集
     * @param i          结果集index
     * @param textArr    工具集
     */
    private static String nature(String text,int i,String[] textArr){
        int i1 = text.indexOf("(");
        i1++;
        String res = null;
        int num = Integer.parseInt(text.substring(i1, text.indexOf(")")));
        res = textArr[num+1].trim();
        text = text.replace("$natureOrder(" + num + ")", res);
        return text;
    }

    /**
     * 索引排序
     * @param text
     * @param i
     * @param textArr
     * @return
     */
    private static String indexOrder(String text,int i,String[] textArr){
        int i1 = text.indexOf("(");
        i1++;
        String res = null;
        Integer num = Integer.parseInt(text.substring(i1, text.indexOf(")")));
        for (int j = 0; j < textArr.length; j++) {
            if(num.toString().equals(textArr[j].trim())){
               res = textArr[j+1].trim();
            }
        }
        text = text.replace("$indexOrder(" + num + ")", res);
        return text;
    }
    /**
     *文本排序(正序和倒序)
     * @param text     结果集
     * @param i          结果集index
     */
    private static String CharOrder(String text,int i,List<String> ls,boolean b){
        int i1 = text.indexOf("(");
        i1++;
        String res = null;
        Integer num = Integer.parseInt(text.substring(i1, text.indexOf(")")));
        String s = ls.get(num);
//        String s = sort(res);
        if(b){
            text = text.replace("$charOrder(" + num + ")", s);
        }else{
            text = text.replace("$charOrderDESC(" + num + ")", s);
        }
        return text;
    }
//    /**
//     *文本倒序
//     * @param text     结果集
//     * @param i          结果集index
//     */
//    private static String CharDesc(String text,int i,List<String> ls){
//        int i1 = text.indexOf("(");
//        i1++;
//        String res = null;
//        Integer num = Integer.parseInt(text.substring(i1, text.indexOf(")")));
//
//
////        text = text.replace("$charOrderDESC(" + num + ")", reverse);
//        return text;
//    }

//    /**
//     * 字符排序方法
//     * @param str
//     * @return
//     */
//    private static String sort(String str) {
//        //把字符串转化成字符数组
//        char[] chs = stringToCharArray(str);
//        //对字符数组进行排序
//        charSort(chs);
//        //把字符数组转化成字符串
//        String s = new String(chs);
//        return s;
//    }
//
//    private static void charSort(char[] chs) {
//        Arrays.sort(chs);
//
//    }
//
//    private static char[] stringToCharArray(String str) {
//        return str.toCharArray();
//    }

}