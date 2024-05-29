package com.example.backend.DTO;

import java.util.List;

public class PagedResponse<T> {
    
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private List<T> items;
    private Double avgTimeToFinishTasks;
    private Double avgTimeToFinishTasksPriorityLow;
    private Double avgTimeToFinishTaskspriorityMedium;
    private Double avgTimeToFinishTaskspriorityHigh; 
    
    
    
    public PagedResponse(int currentPage, int totalPages, int totalItems, List<T> items, Double avgTimeToFinishTasks,
                        Double avgTimeToFinishTasksPriorityLow, Double avgTimeToFinishTaskspriorityMedium,
                        Double avgTimeToFinishTaskspriorityHigh) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.items = items;
        this.avgTimeToFinishTasks = avgTimeToFinishTasks;
        this.avgTimeToFinishTasksPriorityLow = avgTimeToFinishTasksPriorityLow;
        this.avgTimeToFinishTaskspriorityMedium = avgTimeToFinishTaskspriorityMedium;
        this.avgTimeToFinishTaskspriorityHigh = avgTimeToFinishTaskspriorityHigh;
    }


    


    





    public Double getAvgTimeToFinishTasks() {
        return avgTimeToFinishTasks;
    }


    public void setAvgTimeToFinishTasks(Double avgTimeToFinishTasks) {
        this.avgTimeToFinishTasks = avgTimeToFinishTasks;
    }


    public Double getAvgTimeToFinishTasksPriorityLow() {
        return avgTimeToFinishTasksPriorityLow;
    }


    public void setAvgTimeToFinishTasksPriorityLow(Double avgTimeToFinishTasksPriorityLow) {
        this.avgTimeToFinishTasksPriorityLow = avgTimeToFinishTasksPriorityLow;
    }


    public double getAvgTimeToFinishTaskspriorityMedium() {
        return avgTimeToFinishTaskspriorityMedium;
    }


    public void setAvgTimeToFinishTaskspriorityMedium(Double avgTimeToFinishTaskspriorityMedium) {
        this.avgTimeToFinishTaskspriorityMedium = avgTimeToFinishTaskspriorityMedium;
    }


    public Double getAvgTimeToFinishTaskspriorityHigh() {
        return avgTimeToFinishTaskspriorityHigh;
    }


    public void setAvgTimeToFinishTaskspriorityHigh(Double avgTimeToFinishTaskspriorityHigh) {
        this.avgTimeToFinishTaskspriorityHigh = avgTimeToFinishTaskspriorityHigh;
    }

    public int getCurrentPage() {
        return currentPage;
    }


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    public int getTotalPages() {
        return totalPages;
    }


    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


    public int getTotalItems() {
        return totalItems;
    }


    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }


    public List<T> getItems() {
        return items;
    }


    public void setItems(List<T> items) {
        this.items = items;
    }



}
