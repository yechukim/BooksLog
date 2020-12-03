package com.example.bookslog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class Frag_best extends Fragment {

    Button btn_goSite;
    ListView list1;
    MenuItem item_search;
    SearchView search1;
    SearchAdapter searchAdapter;
    List<SearchItems> mList = new ArrayList<>();
    int itemCount;
    SearchItems items;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.bar, menu);

        item_search = menu.findItem(R.id.action_search);
        search1 = (SearchView) item_search.getActionView();
        search1.setQueryHint("책 검색하기");

        SearchViesListener listener = new SearchViesListener();
        search1.setOnQueryTextListener(listener);

    }

    //검색 완료하고 submit 하면 스레드가 query에 해당 키워드가 들어가고 thread가 시작된다.
    class SearchViesListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            NetworkThread thread = new NetworkThread(query);
            thread.start();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    class NetworkThread extends Thread {
        String keyword;
        String client_id = "NI6zpCQXgTOaGarqdtAK";
        String client_secret = "lu3MzDnCUs";

        public NetworkThread(String keyword) {//네트워크 이용할 때는 스레드를 이용한다.
            this.keyword = keyword;
        }

        @Override
        public void run() {
            try {

                //검색어 인코딩
                keyword = URLEncoder.encode(keyword, "UTF-8");
                //접속 주소
                String site = "https://openapi.naver.com/v1/search/book.xml?query=" + keyword + "&display=10" + "&start=1";

                //접속
                URL url = new URL(site);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("X-Naver-Client-Id", client_id);
                conn.setRequestProperty("X-Naver-Client-Secret", client_secret);

                //데이터 읽기
                InputStream is = conn.getInputStream();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(is);

                //최상위 루트태그 가져옴
                Element root = document.getDocumentElement();
                //item 태그 객체 가져옴
                NodeList item_list = root.getElementsByTagName("item");
                //태그 개수만큼 반복
                itemCount = 0;
                for (int i = 0; i < item_list.getLength(); i++) {
                    items = new SearchItems();
                    Element item_tag = (Element) item_list.item(i);
                    NodeList title_list = item_tag.getElementsByTagName("title");
                    NodeList author_list = item_tag.getElementsByTagName("author");
                    NodeList price_list = item_tag.getElementsByTagName("price");
                    NodeList img_list = item_tag.getElementsByTagName("image");


                    Element title_tag = (Element) title_list.item(0);
                    Element link_tag = (Element) author_list.item(0);
                    Element price_tag = (Element) price_list.item(0);
                    Element img_tag = (Element) img_list.item(0);

                    String title = title_tag.getTextContent().replaceAll("\\<.*?>", "");
                    String author = link_tag.getTextContent().replaceAll("\\<.*?>", "");
                    String price = price_tag.getTextContent();
                    String img = img_tag.getTextContent();

                    items.setTitle(title);
                    items.setAuthor("저자: " + author);
                    items.setPrice("가격: " + price);

                    mList.add(items);
                }
                //리스트 뷰를 구성한다.
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchAdapter = (SearchAdapter) list1.getAdapter();
                        searchAdapter.notifyDataSetChanged();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_frag_best, container, false);
        btn_goSite = fragView.findViewById(R.id.btn_goSite);
        setHasOptionsMenu(true);
        //베스트 셀러 사이트로 이동
        btn_goSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://book.naver.com/bestsell/bestseller_list.nhn");
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(siteIntent);
            }
        });
        //리스트뷰
        list1 = fragView.findViewById(R.id.result);
        searchAdapter = new SearchAdapter(getContext(), mList);
        list1.setAdapter(searchAdapter);

        return fragView;
    }

}
