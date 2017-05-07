package com.example.haoyuban111.mubanapplication.help_class;

import com.example.haoyuban111.mubanapplication.glide.CustomImageSizeModelImp;
import com.example.haoyuban111.mubanapplication.model.MomentsModel;
import com.example.haoyuban111.mubanapplication.model.SecondModel;
import com.example.haoyuban111.mubanapplication.model.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/3/27.
 */

public class DataHelper {
    public static List<MomentsModel> getMoments() {
        List<MomentsModel> list = new ArrayList<>();

        List<String> listStr1 = new ArrayList<>();
        MomentsModel model1 = new MomentsModel();
        model1.setSizeModel(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_1));
        model1.setName("num one");
        model1.setText("采菊东篱下，悠然现南山");
//        listStr1.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_1.jpeg");
        listStr1.add(Url.IMAGE_URL_FRANCE_1);
        model1.setImgList(listStr1);
        list.add(model1);

        List<String> listStr2 = new ArrayList<>();
        MomentsModel model2 = new MomentsModel();
        model2.setSizeModel(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_1));
        model2.setName("num two");
        model2.setText("移舟泊烟渚，日暮客愁新");
        listStr2.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_1.jpeg");
        listStr2.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_2.jpeg");
        model2.setImgList(listStr2);
        list.add(model2);

        List<String> listStr3 = new ArrayList<>();
        MomentsModel model3 = new MomentsModel();
        model3.setSizeModel(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_1));
        model3.setName("num three");
        model3.setText("柴米油盐茶，拢捻抹复挑");
        listStr3.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_1.jpeg");
        listStr3.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_2.jpeg");
        listStr3.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_3.jpeg");
        model3.setImgList(listStr3);
        list.add(model3);

        List<String> listStr4 = new ArrayList<>();
        MomentsModel model4 = new MomentsModel();
        model4.setSizeModel(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_1));
        model4.setName("num four");
        model4.setText("天阶夜色凉如水，卧看牵牛织女星");
        listStr4.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_1.jpeg");
        listStr4.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_2.jpeg");
        listStr4.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_3.jpeg");
        listStr4.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_4.jpeg");
        model4.setImgList(listStr4);
        list.add(model4);

        List<String> listStr5 = new ArrayList<>();
        MomentsModel model5 = new MomentsModel();
        model5.setSizeModel(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_1));
        model5.setName("num five");
        model5.setText("天阶夜色凉如水，卧看牵牛织女星");
        listStr5.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_1.jpeg");
        listStr5.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_2.jpeg");
        listStr5.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_3.jpeg");
        listStr5.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_4.jpeg");
        listStr5.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_5.jpeg");
        model5.setImgList(listStr5);
        list.add(model5);

        List<String> listStr6 = new ArrayList<>();
        MomentsModel model6 = new MomentsModel();
        model6.setSizeModel(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_1));
        model6.setName("num six");
        model6.setText("天阶夜色凉如水，卧看牵牛织女星");
        listStr6.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_1.jpeg");
        listStr6.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_2.jpeg");
        listStr6.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_3.jpeg");
        listStr6.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_4.jpeg");
        listStr6.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_5.jpeg");
        listStr6.add("http://wmtp.net/wp-content/uploads/2017/03/0327_xianhua1126_6.jpeg");
        model6.setImgList(listStr6);
        list.add(model6);

        return list;
    }

    public static List<SecondModel> getSecondModels() {
        List<SecondModel> list = new ArrayList<>();

        SecondModel model1 = new SecondModel();
        model1.setText("hello 1");
        model1.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model1.setName("Liszt 1");
        list.add(model1);

        SecondModel model2 = new SecondModel();
        model2.setText("hello 2");
        model2.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model2.setName("Liszt 2");
        list.add(model2);

        SecondModel model3 = new SecondModel();
        model3.setText("hello 3");
        model3.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model3.setName("Liszt 3");
        list.add(model3);

        SecondModel model4 = new SecondModel();
        model4.setText("hello 4");
        model4.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model4.setName("Liszt 4");
        list.add(model4);

        SecondModel model5 = new SecondModel();
        model5.setText("hello 5");
        model5.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model5.setName("Liszt 5");
        list.add(model5);

        SecondModel model6 = new SecondModel();
        model6.setText("hello 1");
        model6.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model6.setName("Liszt 1");
        list.add(model6);

        SecondModel model7 = new SecondModel();
        model7.setText("hello 2");
        model7.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model7.setName("Liszt 2");
        list.add(model7);

        SecondModel model8 = new SecondModel();
        model8.setText("hello 3");
        model8.setAvatarUrl(Url.IMAGE_URL_FRANCE_3);
        model8.setName("Liszt 3");
        list.add(model8);

        SecondModel model9 = new SecondModel();
        model9.setText("hello 4");
        model9.setAvatarUrl(Url.IMAGE_URL_FRANCE_4);
        model9.setName("Liszt 4");
        list.add(model9);

        SecondModel model10 = new SecondModel();
        model10.setText("hello 5");
        model10.setAvatarUrl(Url.IMAGE_URL_FRANCE_3);
        model10.setName("Liszt 5");
        list.add(model10);

        return list;

    }

    public static List<SecondModel> getSecondModels2() {
        List<SecondModel> list = new ArrayList<>();

        SecondModel model1 = new SecondModel();
        model1.setText("Fuck 1");
        model1.setAvatarUrl(Url.IMAGE_URL_FRANCE_1);
        model1.setName("Marks 1");
        list.add(model1);

        SecondModel model2 = new SecondModel();
        model2.setText("Fuck 2");
        model2.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model2.setName("Marks 2");
        list.add(model2);

        SecondModel model3 = new SecondModel();
        model3.setText("Fuck 3");
        model3.setAvatarUrl(Url.IMAGE_URL_FRANCE_3);
        model3.setName("Marks 3");
        list.add(model3);

        SecondModel model4 = new SecondModel();
        model4.setText("Fuck 4");
        model4.setAvatarUrl(Url.IMAGE_URL_FRANCE_4);
        model4.setName("Marks 4");
        list.add(model4);

        SecondModel model5 = new SecondModel();
        model5.setText("Fuck 5");
        model5.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model5.setName("Marks 5");
        list.add(model5);

        SecondModel model6 = new SecondModel();
        model6.setText("Fuck 1");
        model6.setAvatarUrl(Url.IMAGE_URL_FRANCE_3);
        model6.setName("Marks 1");
        list.add(model6);

        SecondModel model7 = new SecondModel();
        model7.setText("Fuck 2");
        model7.setAvatarUrl(Url.IMAGE_URL_FRANCE_4);
        model7.setName("Marks 2");
        list.add(model7);

        SecondModel model8 = new SecondModel();
        model8.setText("Fuck 3");
        model8.setAvatarUrl(Url.IMAGE_URL_FRANCE_1);
        model8.setName("Marks 3");
        list.add(model8);

        SecondModel model9 = new SecondModel();
        model9.setText("Fuck 4");
        model9.setAvatarUrl(Url.IMAGE_URL_FRANCE_2);
        model9.setName("Marks 4");
        list.add(model9);

        SecondModel model10 = new SecondModel();
        model10.setText("Fuck 5");
        model10.setAvatarUrl(Url.IMAGE_URL_FRANCE_4);
        model10.setName("Marks 5");
        list.add(model10);

        return list;

    }

    public static List<String> getStringList() {
        List<String> list = new ArrayList<>();
        list.add("推荐");
        list.add("视频");
        list.add("热门");
        list.add("社会");
        list.add("娱乐");
        list.add("科技");
        list.add("电影");
        list.add("图片");
        list.add("美图");
        list.add("国际");
        list.add("体育");
        list.add("美女");
        list.add("搞笑");
        list.add("故事");
        list.add("美文");
        list.add("教育");
        list.add("养生");
        list.add("奇葩");
        list.add("趣图");
        list.add("时尚");
        list.add("财经");
        list.add("数码");
        list.add("城市");
        list.add("汽车");
        list.add("军事");
        list.add("段子");
        list.add("健康");
        list.add("正能量");
        list.add("健身");
        list.add("房产");
        list.add("历史");
        list.add("育儿");
        list.add("手机");
        list.add("旅游");
        list.add("宠物");
        list.add("情感");
        list.add("家居");
        list.add("文化");
        list.add("游戏");
        list.add("股票");
        list.add("科学");
        list.add("动漫");
        list.add("摄影");
        list.add("语录");
        list.add("星座");
        list.add("炫酷");
        list.add("收藏");
        list.add("两性");
        list.add("女性");
        list.add("心理");

        return list;
    }
}
