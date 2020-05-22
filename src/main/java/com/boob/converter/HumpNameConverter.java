package com.boob.converter;

/**
 * 驼峰命名转换器
 */
public class HumpNameConverter implements NameConverter {

    protected HumpNameConverter() {

    }

    /**
     * 把java命名规则转为database命名规则
     *
     * @param s
     * @return
     */
    @Override
    public String convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();

        //首字母小写
        if ('A' <= chars[0] && chars[0] <= 'Z') {
            sb.append((char) (chars[0] + 32));
        } else {
            sb.append(chars[0]);
        }
        for (int i = 1; i < chars.length; i++) {
            //添加下划线并大写转小写
            if ('A' <= chars[i] && chars[i] <= 'Z') {
                sb.append("_");
                sb.append((char) (chars[i] + 32));
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 把database命名规则转为java命名规则
     *
     * @param o
     * @return
     */
    @Override
    public String reconvert(String o) {
        if (o == null || o.length() == 0) {
            return null;
        }

        char[] chars = o.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            //小写转大写
            if (chars[i - 1] == '_') {
                chars[i] = (char) (chars[i] - 32);
            }
        }
        //去掉下划线
        return new String(chars).replace("_", "");
    }
}
