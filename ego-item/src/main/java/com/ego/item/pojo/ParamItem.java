package com.ego.item.pojo;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/26 23:47
 * @Description:
 * @version: 1.0
 */
public class ParamItem {
    private String group;
    private List<ParamNode> params;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ParamNode> getParams() {
        return params;
    }

    public void setParams(List<ParamNode> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ParamItem{" +
                "group='" + group + '\'' +
                ", params=" + params +
                '}';
    }

    public static String ParamItemToHtmlStr(List<ParamItem> paramItems)
    {
        StringBuffer sf = new StringBuffer();

        for (ParamItem param : paramItems) {
            sf.append("<table width='500' style='color:gray;'>");
            for (int i = 0 ;i<param.getParams().size();i++) {
                if(i==0){
                    sf.append("<tr>");
                    sf.append("<td align='right' width='30%'>"+param.getGroup()+"</td>");
                    sf.append("<td align='right' width='30%'>"+param.getParams().get(i).getK()+"&emsp;&emsp;"+"</td>");
                    sf.append("<td>"+param.getParams().get(i).getV()+"</td>");
                    sf.append("<tr/>");
                }else{
                    sf.append("<tr>");
                    sf.append("<td> </td>");
                    sf.append("<td align='right'>"+param.getParams().get(i).getK()+"&emsp;&emsp;"+"</td>");
                    sf.append("<td>"+param.getParams().get(i).getV()+"</td>");
                    sf.append("</tr>");
                }
            }
            sf.append("</table>");
            sf.append("<hr style='color:gray;'/>");
        }
        return sf.toString();
    }
}
