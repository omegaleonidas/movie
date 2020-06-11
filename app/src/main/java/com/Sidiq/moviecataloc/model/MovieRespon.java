package com.Sidiq.moviecataloc.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieRespon implements Parcelable {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<MoviePopuler> results;

	@SerializedName("total_results")
	private int totalResults;

    protected MovieRespon(Parcel in) {
        page = in.readInt();
        totalPages = in.readInt();
        totalResults = in.readInt();
    }

    public static final Creator<MovieRespon> CREATOR = new Creator<MovieRespon>() {
        @Override
        public MovieRespon createFromParcel(Parcel in) {
            return new MovieRespon(in);
        }

        @Override
        public MovieRespon[] newArray(int size) {
            return new MovieRespon[size];
        }
    };

    public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<MoviePopuler> results){
		this.results = results;
	}

	public List<MoviePopuler> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(totalPages);
        dest.writeInt(totalResults);
    }
}