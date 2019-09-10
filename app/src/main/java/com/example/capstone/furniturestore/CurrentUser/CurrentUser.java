package com.example.capstone.furniturestore.CurrentUser;

import android.text.format.DateFormat;

import com.example.capstone.furniturestore.Models.User;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by amandeepsekhon on 2018-04-12.
 */

public class CurrentUser {
    public static User currentUser;


  public static String getDate(long time)
  {
      Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
      calendar.setTimeInMillis(time);
      StringBuilder date = new StringBuilder(
              android.text.format.DateFormat.format("dd-MM-yyyy HH:mm"
                      ,calendar)
                      .toString());

      return date.toString();
  }
}
