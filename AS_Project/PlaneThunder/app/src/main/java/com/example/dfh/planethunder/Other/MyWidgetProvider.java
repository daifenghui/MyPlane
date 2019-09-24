package com.example.dfh.planethunder.Other;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.dfh.planethunder.Activity.MainActivity;
import com.example.dfh.planethunder.Activity.RankActivity;
import com.example.dfh.planethunder.Activity.RecordActivity;
import com.example.dfh.planethunder.R;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) throws PendingIntent.CanceledException {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_provider);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        Intent clickInt = new Intent(context, RankActivity.class);
        Bundle bundle =new Bundle();
        bundle.putBoolean("languageFlag",true);
        clickInt.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickInt, 0);
        views.setOnClickPendingIntent(R.id.widget_btn, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            try {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

