package com.balance.properties;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class ConstProperties {
    private static ConstProperties constProperties = new ConstProperties();
    private static ConstProperties constProperties_ = new ConstProperties(true);

    private String path;
    private String port;

    ConstProperties(){

    }

    ConstProperties(Boolean flag){
        InputStream in = null;
        try {
            in = this.getClass().getClassLoader()
                    .getResourceAsStream("const.properties");
            Properties properties = new Properties();
            properties.load(in);
            Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Object, Object> entry = it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                Field f = ConstProperties.class.getDeclaredField((String) key);
                f.setAccessible(true);
                f.set(constProperties,value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static ConstProperties getInstance(){
        return constProperties;
    }

    public static void main(String[] args) {
        ConstProperties constProperties = getInstance();
    }
}
