package com.example.chani.logini;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

/*
ViewHolder
RecycerView.ViewHolder
El view holder de las imagenes, funciona para colocar la imagen dentro de nuestra vista
 */
public class ViewHolder extends RecyclerView.ViewHolder {

	View mView;
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		mView = itemView;

	}

	public void setImage(Context ctx, String image, String name){
		ImageView mImageV = mView.findViewById(R.id.ivCardView);
		TextView textView = mView.findViewById(R.id.lblTitleCardView);
		textView.setText(name);
		Picasso.get().load(image).fit().into(mImageV);
	}
}
