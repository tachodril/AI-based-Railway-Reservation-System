package com.sih2020.railwayreservationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sih2020.railwayreservationsystem.Adapters.AlternateRouteAdapter;
import com.sih2020.railwayreservationsystem.Models.AlternateRoutesModel;
import com.sih2020.railwayreservationsystem.R;
import com.sih2020.railwayreservationsystem.Utils.AppConstants;
import com.sih2020.railwayreservationsystem.Utils.GenerateBackground;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AlternateRoutesActivity extends AppCompatActivity {

    ArrayList<AlternateRoutesModel> routes;
    RecyclerView recycler;
    AlternateRouteAdapter alternateRouteAdapter;
    ProgressDialog progressDialog;
    LinearLayout toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternate_routes);

        toolBar=findViewById(R.id.tool_bar_ar);
        toolBar.setBackground(GenerateBackground.generateBackground());

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Asd");
        progressDialog.setCancelable(false);
        progressDialog.show();
        routes=new ArrayList<>();
        fetchAlternateRoutes(this);
        recycler=findViewById(R.id.alternaterouterecycler);
        alternateRouteAdapter =new AlternateRouteAdapter(this,routes);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(alternateRouteAdapter);
        Log.i("onCreate: ","hollllla");

    }



    public void fetchAlternateRoutes(final Context context)
    {
        Log.i("fetchAlternateRoutes: ","jhollaa");
        SimpleDateFormat mformatter=new SimpleDateFormat("EEEE");
        String day=mformatter.format(AppConstants.mDate);
        Date date=new Date();
        String temp=new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
        String current[]=temp.split(" ");
        Log.i( "fetchAlternateRoutes: ",day);
        String uri1= AppConstants.mUrl+"/alternates/"+AppConstants.mSourceStation.getmStationCode().toUpperCase()+"/"+AppConstants.mDestinationStation.getmStationCode().toUpperCase()+"/1/"+AppConstants.getDay(day)+"/120/360";///";//+current[0]+"/"+current[1]+"/"+AppConstants.mDate;
        final String uri2= AppConstants.mUrl+"/alternates/"+AppConstants.mSourceStation.getmStationCode().toUpperCase()+"/"+AppConstants.mDestinationStation.getmStationCode().toUpperCase()+"/2/"+AppConstants.getDay(day)+"/120/360";///"+current[0]+"/"+current[1]+"/"+AppConstants.mDate;
        Log.i( "fetchAlternateRoutes: ",uri1);

        Ion.with(context)
                .load(uri1)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e==null)
                        {   progressDialog.dismiss();
                            AlternateRoutesModel route;
                            JsonArray array=result.getAsJsonArray("alternates");
                            Log.i( "onCompleted:",array.toString());
                            for(int i=0;i<array.size();i++)
                            {   route=new AlternateRoutesModel();
                                JsonObject singleroute=array.get(i).getAsJsonObject();
                                Log.i("Completed: ",singleroute.toString());
                                for(int j=0;j<singleroute.getAsJsonArray("stations").size();j++)
                                {
                                    route.getStations().add(singleroute.getAsJsonArray("stations").get(j).toString());
                                }

                                for(int j=0;j<singleroute.getAsJsonArray("trains").size();j++)
                                {
                                    route.getTrainnos().add(singleroute.getAsJsonArray("trains").get(j).toString());
                                }
                                route.setN(1);
                                route.setTime(singleroute.get("time").getAsString());
                                routes.add(route);

                            }
                            alternateRouteAdapter.notifyDataSetChanged();


//                            if(routes.size()==0)
//                            {
//                                Ion.with(context).load(uri2).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
//                                    @Override
//                                    public void onCompleted(Exception e, JsonObject result) {
//                                       if(e!=null)
//                                       {   progressDialog.dismiss();
//                                           Log.i( "onCompleted0:",result.getAsJsonArray().toString());
//                                           AlternateRoutesModel route=new AlternateRoutesModel();
//                                           JsonArray array=result.getAsJsonArray("alternates");
//                                           for(int i=0;i<array.size();i++)
//                                           {
//                                               JsonObject object=array.get(i).getAsJsonObject();
//                                               for(int j=0;j<object.getAsJsonArray("stations").size();j++)
//                                               {
//                                                   route.getStations().add(object.getAsJsonArray("stations").get(j).toString());
//                                               }
//
//                                               for(int j=0;j<object.getAsJsonArray("trainnos").size();j++)
//                                               {
//                                                   route.getTrainnos().add(object.getAsJsonArray("trainnos").get(j).toString());
//                                               }
//                                               route.setN(2);
//                                               route.setTime(object.get("time").toString());
//                                               routes.add(route);
//
//                                           }
//
//                                           alternateRouteAdapter.notifyDataSetChanged();
//
//                                       }
//                                    }
//                                });
//                            }

                        }
                        else
                        {
//                            Ion.with(context).load(uri2).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
//                                @Override
//                                public void onCompleted(Exception e, JsonObject result) {
//                                    if(e!=null)
//                                    {   progressDialog.dismiss();
//                                        Log.i( "onCompleted1:",result.getAsJsonArray().toString());
//                                        AlternateRoutesModel route=new AlternateRoutesModel();
//                                        JsonArray array=result.getAsJsonArray("alternates");
//                                        for(int i=0;i<array.size();i++)
//                                        {
//                                            JsonObject object=array.get(i).getAsJsonObject();
//                                            for(int j=0;j<object.getAsJsonArray("stations").size();j++)
//                                            {
//                                                route.getStations().add(object.getAsJsonArray("stations").get(j).toString());
//                                            }
//
//                                            for(int j=0;j<object.getAsJsonArray("trainnos").size();j++)
//                                            {
//                                                route.getTrainnos().add(object.getAsJsonArray("trainnos").get(j).toString());
//                                            }
//                                            route.setN(2);
//                                            route.setTime(object.get("time").toString());
//                                            routes.add(route);
//
//                                        }
//
//                                        alternateRouteAdapter.notifyDataSetChanged();
//
//
//
//                                    }
//                                    else
//                                    {
//                                        //Unable to fetch
//                                        progressDialog.dismiss();
//                                        Log.i("onCompleted: ",e.getMessage());
//                                    }
//                                }
//
//                            });

                            progressDialog.dismiss();
                            Log.i("onCompleted:kj ",e.getMessage());
                        }
                    }
                });

    }
}
