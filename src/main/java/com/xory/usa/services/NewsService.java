package com.xory.usa.services;


import com.xory.usa.models.News;
import com.xory.usa.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllNews(){
        return newsRepository.findAll();
    }
    public void addNews(News news){
        newsRepository.save(news);
    }

}
