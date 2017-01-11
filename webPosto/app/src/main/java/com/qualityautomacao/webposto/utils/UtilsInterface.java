package com.qualityautomacao.webposto.utils;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public abstract class UtilsInterface {

    public static void setDateCalendario(final EditText editText) {

        setDateCalendario(editText, new Supplier<String>() {
            @Override
            public String get() {
                return editText.getText().toString();
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s){
                editText.setText(s);
            }
        });
    }

    public static void setDateCalendario(final View view, final Supplier<String> getter, final Consumer<String> setter) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                try {
                    cal.setTime(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(getter.get()));
                } catch (ParseException e) {
                    Log.e("WEB_POSTO", "onClick: " + e.getMessage());
                }

                int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        Calendar data = new GregorianCalendar(ano, mes, dia);
                        try {
                            setter.accept(new SimpleDateFormat("dd/MM/yyyy").format(data.getTime()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);

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
