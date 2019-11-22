package com.example.rf.tutioncounter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by RF on 8/19/2017.
 */

public abstract class utilityMethods {
    public static int[] DaysNumGenerate(String d){
        int combine=Integer.parseInt(d);
        int[] days=new int[d.length()];
        for (int i=d.length()-1;i>=0;i--){
            double dividend=Math.pow(10,i);
            days[i]= (int) (combine/dividend);
            combine= (int) (combine%dividend);
        }
        return days;
    }
    public static String E2BnumberConverter(String Enum){
        String Bnum="";
        for (int i=0;i<Enum.length();i++){
            Bnum+=CONSTANT_VALUES.NUMBERS[Enum.charAt(i)-'0'][1];
        }
        return Bnum;
    }
    public static byte[] marshall(Parcelable parceable) {
        Parcel parcel = Parcel.obtain();
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }
    public static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // This is extremely important!
        return parcel;
    }
    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }
    public static void calendarDetails(int[] calendar){
        Calendar cal = Calendar.getInstance();
        calendar[0]=cal.get(Calendar.DAY_OF_WEEK);
        calendar[1]=cal.get(Calendar.HOUR_OF_DAY);
        calendar[2]=cal.get(Calendar.MINUTE);
        calendar[3]=cal.get(Calendar.SECOND);
        calendar[4]=cal.get(Calendar.DAY_OF_MONTH);
        calendar[5]=cal.get(Calendar.MONTH)+1;
        calendar[6]=cal.get(Calendar.YEAR);
        calendar[7]=cal.get(Calendar.DAY_OF_WEEK);
    }

}
