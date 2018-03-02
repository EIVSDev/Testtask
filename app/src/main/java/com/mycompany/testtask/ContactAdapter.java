package com.mycompany.testtask;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Slava on 26.02.2018.
 */

public class ContactAdapter extends ArrayAdapter<ContactPOJO> {

    List<ContactPOJO>contactList;
    Context context;
    private LayoutInflater mInflater;

    public ContactAdapter(@NonNull Context context, List<ContactPOJO> objects) {
        super(context, 0, objects);
        this.contactList = contactList;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Nullable
    @Override
    public ContactPOJO getItem(int position) {
        return contactList.get(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final  ViewHolder vh;
        if(convertView ==null){
        View view = mInflater.inflate(R.layout.layout_row_view,parent,false);
        vh=ViewHolder.create((RelativeLayout) view);
        view.setTag(vh);
        }else {
            vh= (ViewHolder)convertView.getTag();
        }

         ContactPOJO item = getItem(position);
        vh.textViewName.setText(item.getName());
        vh.textViewEmail.setText(item.getEmail());
        Picasso.with(getContext()).load("https://avatars.io/twitter/4").error(R.mipmap.ic_launcher).into(vh.imagView);
        return vh.rootView;
    }

    private  static class ViewHolder{
        public final RelativeLayout rootView;
        public  final ImageView imagView;
        public  final TextView textViewName;
        public  final TextView textViewEmail;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewEmail) {
            this.rootView = rootView;
            this.imagView = imageView;
            this.textViewName = textViewName;
            this.textViewEmail = textViewEmail;
        }

        public static ViewHolder create(RelativeLayout rootView){
          ImageView imageView =(ImageView)rootView.findViewById(R.id.imageView);
          TextView textViewName =(TextView) rootView.findViewById(R.id.textViewName);
          TextView textViewEmail =(TextView) rootView.findViewById(R.id.textViewEmail);

           return new ViewHolder(rootView, imageView,textViewName,textViewEmail);
        }

    }

}
