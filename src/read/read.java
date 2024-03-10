package read;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:关于获取文本文件中的数据的函数->转化成字符串，使用正则处理数据的函数，获得有效信息（即每个物体的具体信息）的函数
 * 均为静态函数，便于其他类的调用
 * User: xinyu
 * Date: 2023-04-18
 * Time: 18:25
 */
public class read {
    /**
     * 读文本文件的数据并将文本文件的数据转化成字符串
     * @param file 要读的文件
     * @return 文本文件的数据转化成的字符串
     */
    public static String getDataToString(String file) throws FileNotFoundException{
        //用于存放最终要转成的字符串
        String ObjectString=new String();
        String stringLine=new String();
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader=new FileReader(new File(file));
            bufferedReader=new BufferedReader(fileReader);
            //读文件，当文件不空时
            while ((stringLine=bufferedReader.readLine())!=null){
                ObjectString+=stringLine;
                ObjectString+="\n";
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {//关闭文件
            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ObjectString;
    }

    /**
     * 使用正则表达式，将文本文件匹配成相应的数据结构
     * @param file 文本文件
     * @return 元素类型是Pair的列表
     * 我们的输入文件：常规思路就是::=前面是key,后面是value，
     * 可以用pair，key是Athlete，value是<Bolt,1,JAM,38,9.94>，可以看作是数组
     * Pair<String, List<String>>对应的就是Athlete ::= <Bolt,1,JAM,38,9.94>
     * 返回结果举例子：
     * 		{ a-{p,o,i} , b-{u,y,t} , c-{r,e,w} }
     * 		   0            1           2
     */
    public static List<Pair<String,List<String>>> handleByRegular(String file){
        String ObjectString;
        try {
            ObjectString=getDataToString(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Pair<String,List<String>>> ObjectList=new ArrayList<>();
        //先匹配::=前面的
        String pattern1="([a-z,A-Z,0-9]+)( +)?:";//a-z,A-Z,0-9一次或多次出现； （空格）一次或多次出现或不出现---》直接用 （空格）*
        Pattern r1 = Pattern.compile(pattern1);//匹配的是::=前面的
        Matcher m1 = r1.matcher(ObjectString);
// Pattern类的作用在于编译正则表达式后创建一个匹配模式.
//Matcher类使用Pattern实例提供的模式信息对正则表达式进行匹配
        String pattern2="=( +)?(.*)";
        Pattern r2=Pattern.compile(pattern2);
        Matcher m2=r2.matcher(ObjectString);//匹配的是整个
//boolean find() 对字符串进行匹配,匹配到的字符串可以在任何位置
        while(m1.find()){
            m2.find();
//涉及group用法：从正则表达式左侧开始，每出现一个左括号"("记做一个分组，分组编号从 1 开始。0 代表整个表达式。
//m2.group(2)是<Bolt,1,JAM,38,9.94>
            List<String> tmpList=getAllParts(m2.group(2));
//m1.group(1)是Athlete
            String tmpString=m1.group(1);
            ObjectList.add(new Pair<>(tmpString,tmpList));

        }
        return ObjectList;
    }
    //函数将<Bolt,1,JAM,38,9.94>放入一个数组里面
    public static List<String> getAllParts(String rawString){
        String[] tmpString;
        List<String> reList=new ArrayList<>();
        tmpString=rawString.split("\\s*[;,<> ]+\\s*");
        for(String eachString:tmpString){
            if(eachString.hashCode()!=0)//这里与别的类实现有关
                reList.add(eachString);
        }
        return reList;
    }


}
