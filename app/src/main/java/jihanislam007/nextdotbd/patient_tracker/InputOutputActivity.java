package jihanislam007.nextdotbd.patient_tracker;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class InputOutputActivity extends AppCompatActivity {

    ArrayList<NoteModel> arrayList;
    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    DatabaseHelper database_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_output);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                showDialog();
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        actionButton = (FloatingActionButton) findViewById(R.id.add);
        database_helper = new DatabaseHelper(this);
        displayNotes();

        /*actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });*/
    }

    //display notes list
    public void displayNotes() {
        arrayList = new ArrayList<>(database_helper.getNotes());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        NotesAdapter adapter = new NotesAdapter(getApplicationContext(), this, arrayList);
        recyclerView.setAdapter(adapter);
    }

    //display dialog
    public void showDialog() {
        final EditText patient_name,phone ,  age , arrivalDate , disease, medication;
        Button submit;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.dialog);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        patient_name = (EditText) dialog.findViewById(R.id.title);
        phone = (EditText) dialog.findViewById(R.id.phone);
        age = (EditText) dialog.findViewById(R.id.Age);
        arrivalDate = (EditText) dialog.findViewById(R.id.Arrival_date);
        disease = (EditText) dialog.findViewById(R.id.Disease);
        medication = (EditText) dialog.findViewById(R.id.Medication);
        submit = (Button) dialog.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                if (patient_name.getText().toString().isEmpty()) {
                    patient_name.setError("Please Enter Title");
                }else if(disease.getText().toString().isEmpty()) {
                    disease.setError("Please Enter Description");
                }else if(medication.getText().toString().isEmpty()) {
                    medication.setError("Please Enter Description");
                }else {
                    database_helper.addNotes(patient_name.getText().toString(), phone.getText().toString(),age.getText().toString(),arrivalDate.getText().toString(),disease.getText().toString(), medication.getText().toString());
                    dialog.cancel();
                    displayNotes();
                }
            }
        });
    }
}