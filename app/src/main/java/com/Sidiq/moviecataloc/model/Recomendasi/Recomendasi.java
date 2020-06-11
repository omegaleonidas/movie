package com.Sidiq.moviecataloc.model.Recomendasi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Recomendasi implements Parcelable {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<Recomendation> results;

	@SerializedName("total_results")
	private int totalResults;

	protected Recomendasi(Parcel in) {
		page = in.readInt();
		totalPages = in.readInt();
		results = in.createTypedArrayList(Recomendation.CREATOR);
		totalResults = in.readInt();
	}

	public static final Creator<Recomendasi> CREATOR = new Creator<Recomendasi>() {
		@Override
		public Recomendasi createFromParcel(Parcel in) {
			return new Recomendasi(in);
		}

		@Override
		public Recomendasi[] newArray(int size) {
			return new Recomendasi[size];
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

	public void setResults(List<Recomendation> results){
		this.results = results;
	}

	public List<Recomendation> getResults(){
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
		dest.writeTypedList(results);
		dest.writeInt(totalResults);
	}
}