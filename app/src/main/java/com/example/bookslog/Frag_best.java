package com.example.bookslog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;

public class Frag_best extends Fragment {
    Button btn_goSite, btn_search;
    EditText keyword;
    ListView result;
    String sKeyword, str;
    StringBuffer sb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragView = inflater.inflate(R.layout.fragment_frag_best, container, false);
        btn_goSite = fragView.findViewById(R.id.btn_goSite);
        //베스트 셀러 사이트로 이동
        btn_goSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://book.naver.com/bestsell/bestseller_list.nhn");
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(siteIntent);
            }
        });
        // 책 정보 검색하기
        btn_search = fragView.findViewById(R.id.btn_search);
        keyword = fragView.findViewById(R.id.keyword);
        result = fragView.findViewById(R.id.result);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {

                    @Override
                    public void run() {
                        sKeyword = keyword.getText().toString();
                        if (sKeyword.isEmpty()) {
                            Snackbar snackbar = Snackbar.make(getView(), "검색어를 입력하세요", BaseTransientBottomBar.LENGTH_SHORT);
                            snackbar.show();
                        } else {
                            str = getSearch(sKeyword);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<StringBuffer> adapter;
                                    adapter = new ArrayAdapter<StringBuffer>(getActivity(), android.R.layout.simple_expandable_list_item_1, Collections.singletonList(sb));
                                    result.setAdapter(adapter);
                                }
                            });
                        }
                    }
                }.start();

            }
        });
        return fragView;
    }

    public String getSearch(String keyword) {
        String clientID = "NI6zpCQXgTOaGarqdtAK";
        String clientSecret = "lu3MzDnCUs";
         sb = new StringBuffer();

        try {

            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/book.xml?query=" + text + "&display=10" + "&start=1";
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", clientID);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            String tag;
            //inputStream으로부터 xml값 받기
            xpp.setInput(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기

                        if (tag.equals("item")) ; //첫번째 검색 결과

                        else if (tag.equals("title")) {
                            sb.append("책 제목 : ");
                            xpp.next();
                            sb.append(xpp.getText().replaceAll("\\<.*?>", ""));
                            sb.append("\n");
                            sb.append("\n");
                        } else if (tag.equals("author")) {
                            sb.append("저자 정보 : ");
                            xpp.next();
                            sb.append(xpp.getText());
                            sb.append("\n");
                            sb.append("\n");
                        } else if (tag.equals("price")) {
                            sb.append("가격 : ");
                            xpp.next();
                            sb.append(xpp.getText() + "원");
                            sb.append("\n");
                            sb.append("\n");
                        }
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            return e.toString();
        }
        return sb.toString();
    }

}