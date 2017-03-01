package lantian.calendarcolor;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int REQUEST_CALENDAR_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_CALENDAR,
                            Manifest.permission.WRITE_CALENDAR
                    }, REQUEST_CALENDAR_PERMISSION);
        } else {
            showCalendars();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALENDAR_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCalendars();
            } else {
                this.finish();
            }
        }
    }

    void showCalendars() {
        ArrayList<CalendarEntry> calendarEntries = new ArrayList<>();

        String[] columns = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.CALENDAR_COLOR
        };
        try {
            Cursor cursor = getContentResolver().query(
                    CalendarContract.Calendars.CONTENT_URI,
                    columns,
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int col_id = cursor.getColumnIndex(columns[0]);
                int col_name = cursor.getColumnIndex(columns[1]);
                int col_color = cursor.getColumnIndex(columns[2]);

                do {
                    calendarEntries.add(new CalendarEntry(
                            cursor.getString(col_id),
                            cursor.getString(col_name),
                            cursor.getString(col_color)));
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (SecurityException se) {
            MainActivity.this.finish();
        }
        ListView lv = (ListView) findViewById(R.id.calendarList);
        lv.setAdapter(new CalendarAdapter(this, calendarEntries));
    }

    class CalendarEntry {
        String id;
        String title;
        String color;

        CalendarEntry(String id, String title, String color) {
            this.id = id;
            this.title = title;
            this.color = color;
        }

        public String toString() {
            return title;
        }
    }

    class CalendarAdapter extends ArrayAdapter<CalendarEntry> {
        CalendarAdapter(Context context, ArrayList<CalendarEntry> entry) {
            super(context, 0, entry);
        }

        @Override
        @SuppressWarnings("ConstantConditions")
        public
        @NonNull
        View getView(final int position, View view, @NonNull ViewGroup parent) {
            CalendarEntry entry = getItem(position);
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.entry_calendar, parent, false);
            }
            TextView tv = (TextView) view.findViewById(R.id.calendarTitle);
            tv.setText(entry.title);
            tv.setTextColor(Integer.valueOf(entry.color));
            ImageView iv = (ImageView) view.findViewById(R.id.calendarColor);
            iv.setColorFilter(new LightingColorFilter(
                    Color.parseColor(String.format("#%06X", (0xFFFFFF & Integer.valueOf(entry.color)))),
                    Color.parseColor(String.format("#%06X", (0xFFFFFF & Integer.valueOf(entry.color))))
            ));
            tv.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("ConstantConditions")
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.choose_color);
                    ((TextView) dialog.findViewById(R.id.calendarName)).setText(getItem(position).title);
                    regColorSet(dialog, R.id.colorSetRed,
                            R.color.materialRed50,
                            R.color.materialRed100,
                            R.color.materialRed200,
                            R.color.materialRed300,
                            R.color.materialRed400,
                            R.color.materialRed500,
                            R.color.materialRed600,
                            R.color.materialRed700,
                            R.color.materialRed800,
                            R.color.materialRed900,
                            R.color.materialRedA100,
                            R.color.materialRedA200,
                            R.color.materialRedA400,
                            R.color.materialRedA700);
                    regColorSet(dialog, R.id.colorSetPink,
                            R.color.materialPink50,
                            R.color.materialPink100,
                            R.color.materialPink200,
                            R.color.materialPink300,
                            R.color.materialPink400,
                            R.color.materialPink500,
                            R.color.materialPink600,
                            R.color.materialPink700,
                            R.color.materialPink800,
                            R.color.materialPink900,
                            R.color.materialPinkA100,
                            R.color.materialPinkA200,
                            R.color.materialPinkA400,
                            R.color.materialPinkA700);
                    regColorSet(dialog, R.id.colorSetPurple,
                            R.color.materialPurple50,
                            R.color.materialPurple100,
                            R.color.materialPurple200,
                            R.color.materialPurple300,
                            R.color.materialPurple400,
                            R.color.materialPurple500,
                            R.color.materialPurple600,
                            R.color.materialPurple700,
                            R.color.materialPurple800,
                            R.color.materialPurple900,
                            R.color.materialPurpleA100,
                            R.color.materialPurpleA200,
                            R.color.materialPurpleA400,
                            R.color.materialPurpleA700);
                    regColorSet(dialog, R.id.colorSetDeepPurple,
                            R.color.materialDeepPurple50,
                            R.color.materialDeepPurple100,
                            R.color.materialDeepPurple200,
                            R.color.materialDeepPurple300,
                            R.color.materialDeepPurple400,
                            R.color.materialDeepPurple500,
                            R.color.materialDeepPurple600,
                            R.color.materialDeepPurple700,
                            R.color.materialDeepPurple800,
                            R.color.materialDeepPurple900,
                            R.color.materialDeepPurpleA100,
                            R.color.materialDeepPurpleA200,
                            R.color.materialDeepPurpleA400,
                            R.color.materialDeepPurpleA700);
                    regColorSet(dialog, R.id.colorSetIndigo,
                            R.color.materialIndigo50,
                            R.color.materialIndigo100,
                            R.color.materialIndigo200,
                            R.color.materialIndigo300,
                            R.color.materialIndigo400,
                            R.color.materialIndigo500,
                            R.color.materialIndigo600,
                            R.color.materialIndigo700,
                            R.color.materialIndigo800,
                            R.color.materialIndigo900,
                            R.color.materialIndigoA100,
                            R.color.materialIndigoA200,
                            R.color.materialIndigoA400,
                            R.color.materialIndigoA700);
                    regColorSet(dialog, R.id.colorSetBlue,
                            R.color.materialBlue50,
                            R.color.materialBlue100,
                            R.color.materialBlue200,
                            R.color.materialBlue300,
                            R.color.materialBlue400,
                            R.color.materialBlue500,
                            R.color.materialBlue600,
                            R.color.materialBlue700,
                            R.color.materialBlue800,
                            R.color.materialBlue900,
                            R.color.materialBlueA100,
                            R.color.materialBlueA200,
                            R.color.materialBlueA400,
                            R.color.materialBlueA700);
                    regColorSet(dialog, R.id.colorSetLightBlue,
                            R.color.materialLightBlue50,
                            R.color.materialLightBlue100,
                            R.color.materialLightBlue200,
                            R.color.materialLightBlue300,
                            R.color.materialLightBlue400,
                            R.color.materialLightBlue500,
                            R.color.materialLightBlue600,
                            R.color.materialLightBlue700,
                            R.color.materialLightBlue800,
                            R.color.materialLightBlue900,
                            R.color.materialLightBlueA100,
                            R.color.materialLightBlueA200,
                            R.color.materialLightBlueA400,
                            R.color.materialLightBlueA700);
                    regColorSet(dialog, R.id.colorSetCyan,
                            R.color.materialCyan50,
                            R.color.materialCyan100,
                            R.color.materialCyan200,
                            R.color.materialCyan300,
                            R.color.materialCyan400,
                            R.color.materialCyan500,
                            R.color.materialCyan600,
                            R.color.materialCyan700,
                            R.color.materialCyan800,
                            R.color.materialCyan900,
                            R.color.materialCyanA100,
                            R.color.materialCyanA200,
                            R.color.materialCyanA400,
                            R.color.materialCyanA700);
                    regColorSet(dialog, R.id.colorSetTeal,
                            R.color.materialTeal50,
                            R.color.materialTeal100,
                            R.color.materialTeal200,
                            R.color.materialTeal300,
                            R.color.materialTeal400,
                            R.color.materialTeal500,
                            R.color.materialTeal600,
                            R.color.materialTeal700,
                            R.color.materialTeal800,
                            R.color.materialTeal900,
                            R.color.materialTealA100,
                            R.color.materialTealA200,
                            R.color.materialTealA400,
                            R.color.materialTealA700);
                    regColorSet(dialog, R.id.colorSetGreen,
                            R.color.materialGreen50,
                            R.color.materialGreen100,
                            R.color.materialGreen200,
                            R.color.materialGreen300,
                            R.color.materialGreen400,
                            R.color.materialGreen500,
                            R.color.materialGreen600,
                            R.color.materialGreen700,
                            R.color.materialGreen800,
                            R.color.materialGreen900,
                            R.color.materialGreenA100,
                            R.color.materialGreenA200,
                            R.color.materialGreenA400,
                            R.color.materialGreenA700);
                    regColorSet(dialog, R.id.colorSetLightGreen,
                            R.color.materialLightGreen50,
                            R.color.materialLightGreen100,
                            R.color.materialLightGreen200,
                            R.color.materialLightGreen300,
                            R.color.materialLightGreen400,
                            R.color.materialLightGreen500,
                            R.color.materialLightGreen600,
                            R.color.materialLightGreen700,
                            R.color.materialLightGreen800,
                            R.color.materialLightGreen900,
                            R.color.materialLightGreenA100,
                            R.color.materialLightGreenA200,
                            R.color.materialLightGreenA400,
                            R.color.materialLightGreenA700);
                    regColorSet(dialog, R.id.colorSetLime,
                            R.color.materialLime50,
                            R.color.materialLime100,
                            R.color.materialLime200,
                            R.color.materialLime300,
                            R.color.materialLime400,
                            R.color.materialLime500,
                            R.color.materialLime600,
                            R.color.materialLime700,
                            R.color.materialLime800,
                            R.color.materialLime900,
                            R.color.materialLimeA100,
                            R.color.materialLimeA200,
                            R.color.materialLimeA400,
                            R.color.materialLimeA700);
                    regColorSet(dialog, R.id.colorSetYellow,
                            R.color.materialYellow50,
                            R.color.materialYellow100,
                            R.color.materialYellow200,
                            R.color.materialYellow300,
                            R.color.materialYellow400,
                            R.color.materialYellow500,
                            R.color.materialYellow600,
                            R.color.materialYellow700,
                            R.color.materialYellow800,
                            R.color.materialYellow900,
                            R.color.materialYellowA100,
                            R.color.materialYellowA200,
                            R.color.materialYellowA400,
                            R.color.materialYellowA700);
                    regColorSet(dialog, R.id.colorSetAmber,
                            R.color.materialAmber50,
                            R.color.materialAmber100,
                            R.color.materialAmber200,
                            R.color.materialAmber300,
                            R.color.materialAmber400,
                            R.color.materialAmber500,
                            R.color.materialAmber600,
                            R.color.materialAmber700,
                            R.color.materialAmber800,
                            R.color.materialAmber900,
                            R.color.materialAmberA100,
                            R.color.materialAmberA200,
                            R.color.materialAmberA400,
                            R.color.materialAmberA700);
                    regColorSet(dialog, R.id.colorSetOrange,
                            R.color.materialOrange50,
                            R.color.materialOrange100,
                            R.color.materialOrange200,
                            R.color.materialOrange300,
                            R.color.materialOrange400,
                            R.color.materialOrange500,
                            R.color.materialOrange600,
                            R.color.materialOrange700,
                            R.color.materialOrange800,
                            R.color.materialOrange900,
                            R.color.materialOrangeA100,
                            R.color.materialOrangeA200,
                            R.color.materialOrangeA400,
                            R.color.materialOrangeA700);
                    regColorSet(dialog, R.id.colorSetDeepOrange,
                            R.color.materialDeepOrange50,
                            R.color.materialDeepOrange100,
                            R.color.materialDeepOrange200,
                            R.color.materialDeepOrange300,
                            R.color.materialDeepOrange400,
                            R.color.materialDeepOrange500,
                            R.color.materialDeepOrange600,
                            R.color.materialDeepOrange700,
                            R.color.materialDeepOrange800,
                            R.color.materialDeepOrange900,
                            R.color.materialDeepOrangeA100,
                            R.color.materialDeepOrangeA200,
                            R.color.materialDeepOrangeA400,
                            R.color.materialDeepOrangeA700);
                    regColorSet(dialog, R.id.colorSetBrown,
                            R.color.materialBrown50,
                            R.color.materialBrown100,
                            R.color.materialBrown200,
                            R.color.materialBrown300,
                            R.color.materialBrown400,
                            R.color.materialBrown500,
                            R.color.materialBrown600,
                            R.color.materialBrown700,
                            R.color.materialBrown800,
                            R.color.materialBrown900,
                            android.R.color.white,
                            android.R.color.black,
                            android.R.color.white,
                            android.R.color.black);
                    regColorSet(dialog, R.id.colorSetGrey,
                            R.color.materialGrey50,
                            R.color.materialGrey100,
                            R.color.materialGrey200,
                            R.color.materialGrey300,
                            R.color.materialGrey400,
                            R.color.materialGrey500,
                            R.color.materialGrey600,
                            R.color.materialGrey700,
                            R.color.materialGrey800,
                            R.color.materialGrey900,
                            android.R.color.white,
                            android.R.color.black,
                            android.R.color.white,
                            android.R.color.black);
                    regColorSet(dialog, R.id.colorSetBlueGrey,
                            R.color.materialBlueGrey50,
                            R.color.materialBlueGrey100,
                            R.color.materialBlueGrey200,
                            R.color.materialBlueGrey300,
                            R.color.materialBlueGrey400,
                            R.color.materialBlueGrey500,
                            R.color.materialBlueGrey600,
                            R.color.materialBlueGrey700,
                            R.color.materialBlueGrey800,
                            R.color.materialBlueGrey900,
                            android.R.color.white,
                            android.R.color.black,
                            android.R.color.white,
                            android.R.color.black);
                    regColorSet(dialog, R.id.colorSetBlack,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black,
                            android.R.color.black);
                    regColorSet(dialog, R.id.colorSetWhite,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white,
                            android.R.color.white);
                    dialog.show();
                }

                void regColorSet(final Dialog dialog, final int colorSetID,
                                 final int colorAccent50ID,
                                 final int colorAccent100ID,
                                 final int colorAccent200ID,
                                 final int colorAccent300ID,
                                 final int colorAccent400ID,
                                 final int colorAccent500ID,
                                 final int colorAccent600ID,
                                 final int colorAccent700ID,
                                 final int colorAccent800ID,
                                 final int colorAccent900ID,
                                 final int colorAccentA100ID,
                                 final int colorAccentA200ID,
                                 final int colorAccentA400ID,
                                 final int colorAccentA700ID) {
                    ImageView iv = (ImageView) dialog.findViewById(colorSetID);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            regColorAccent(dialog, R.id.colorAccent50, colorAccent50ID);
                            regColorAccent(dialog, R.id.colorAccent100, colorAccent100ID);
                            regColorAccent(dialog, R.id.colorAccent200, colorAccent200ID);
                            regColorAccent(dialog, R.id.colorAccent300, colorAccent300ID);
                            regColorAccent(dialog, R.id.colorAccent400, colorAccent400ID);
                            regColorAccent(dialog, R.id.colorAccent500, colorAccent500ID);
                            regColorAccent(dialog, R.id.colorAccent600, colorAccent600ID);
                            regColorAccent(dialog, R.id.colorAccent700, colorAccent700ID);
                            regColorAccent(dialog, R.id.colorAccent800, colorAccent800ID);
                            regColorAccent(dialog, R.id.colorAccent900, colorAccent900ID);
                            regColorAccent(dialog, R.id.colorAccentA100, colorAccentA100ID);
                            regColorAccent(dialog, R.id.colorAccentA200, colorAccentA200ID);
                            regColorAccent(dialog, R.id.colorAccentA400, colorAccentA400ID);
                            regColorAccent(dialog, R.id.colorAccentA700, colorAccentA700ID);
                            dialog.findViewById(R.id.textView2).setVisibility(View.VISIBLE);
                            dialog.findViewById(R.id.accentSet1).setVisibility(View.VISIBLE);
                            dialog.findViewById(R.id.accentSet2).setVisibility(View.VISIBLE);
                        }
                    });
                }

                void regColorAccent(final Dialog dialog, final int colorAccentViewID, final int colorAccentID) {
                    ImageView iv = (ImageView) dialog.findViewById(colorAccentViewID);
                    iv.setImageResource(colorAccentID);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @SuppressWarnings({"deprecation", "ConstantConditions"})
                        @Override
                        public void onClick(View v) {
                            CalendarEntry cal = getItem(position);
                            // From http://stackoverflow.com/questions/31590714/getcolorint-id-deprecated-on-android-6-0-marshmallow-api-23
                            int color;
                            if (Build.VERSION.SDK_INT >= 23) {
                                color = getColor(colorAccentID);
                            } else {
                                color = getContext().getResources().getColor(colorAccentID);
                            }

                            ContentValues values = new ContentValues();
                            // The new display name for the calendar
                            values.put(CalendarContract.Calendars.CALENDAR_COLOR, String.valueOf(color));
                            Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, Integer.valueOf(getItem(position).id));
                            getContentResolver().update(updateUri, values, null, null);

                            dialog.dismiss();
                            showCalendars();
                        }
                    });
                }
            });
            return view;
        }
    }
}
