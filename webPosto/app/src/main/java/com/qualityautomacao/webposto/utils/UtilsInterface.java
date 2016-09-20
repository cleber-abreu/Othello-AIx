package com.qualityautomacao.webposto.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class UtilsInterface {

    public static void setDateCalendario(final Context view, final EditText editText) {

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(view, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        Calendar data = new GregorianCalendar(ano, mes, dia);
                        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                        editText.setText(fmt.format(data.getTime()));
                    }
                }, mYear, mMonth, mDay);

                mDatePicker.setTitle("Selecione uma data");
                mDatePicker.show();
            }
        });

    }

    public static String setDate(){

        long currentDate = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formatDate = sdf.format(currentDate);
        return formatDate;
    }

    public static String setSevenDaysBefore(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = sdf.parse(setDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -7);

        return sdf.format(calendar.getTime());
    }

//    public static void setAbas(FragmentActivity activity, int fragmento, TabAction... acoes) {
//        ViewPager viewPager = (ViewPager) activity.findViewById(R.id.viewpager_vendas);
//        viewPager.setAdapter(new SampleFragmentPagerAdapter(activity.getSupportFragmentManager(), activity, fragmento, acoes));
//
//        TabLayout tabLayout = (TabLayout) activity.findViewById(R.id.sliding_tabs_vendas);
//        tabLayout.setupWithViewPager(viewPager);
//    }
}
