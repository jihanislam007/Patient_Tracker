package jihanislam007.nextdotbd.patient_tracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.viewHolder> {

    Context context;
    Activity activity;
    ArrayList<NoteModel> arrayList;
    DatabaseHelper database_helper;

    public NotesAdapter(Context context,Activity activity, ArrayList<NoteModel> arrayList) {
        this.context = context;
        this.activity  = activity ;
        this.arrayList = arrayList;
    }

    @Override
    public NotesAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_list, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotesAdapter.viewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.title.setText(arrayList.get(position).getPatient_name());
        holder.phone.setText(arrayList.get(position).getPhone());
        holder.age.setText(arrayList.get(position).getAge());
        holder.arrivalDate.setText(arrayList.get(position).getArrival_date());
        holder.disease.setText(arrayList.get(position).getDisease());
        holder.medication.setText(arrayList.get(position).getMedication());
        database_helper = new DatabaseHelper(context);

        holder.delete.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                //deleting note
                database_helper.delete(arrayList.get(position).getID());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                //display edit dialog
                showDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title,phone ,  age , arrivalDate , disease, medication;
        ImageView delete, edit;
        public viewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            phone = (TextView) itemView.findViewById(R.id.phone);
            age = (TextView) itemView.findViewById(R.id.age);
            arrivalDate = (TextView) itemView.findViewById(R.id.arrivalDate);
            disease = (TextView) itemView.findViewById(R.id.disease);
            medication = (TextView) itemView.findViewById(R.id.medication);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            edit = (ImageView) itemView.findViewById(R.id.edit);
        }
    }

    public void showDialog(final int pos) {
        final EditText patient_name, phone ,  age , arrivalDate ,  disease, medication;
        Button submit;
        final Dialog dialog = new Dialog(activity);
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

        patient_name.setText(arrayList.get(pos).getPatient_name());
        phone.setText(arrayList.get(pos).getPhone());
        age.setText(arrayList.get(pos).getAge());
        arrivalDate.setText(arrayList.get(pos).getArrival_date());
        disease.setText(arrayList.get(pos).getDisease());
        medication.setText(arrayList.get(pos).getMedication());

        submit.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                if (patient_name.getText().toString().isEmpty()) {
                    patient_name.setError("Please Enter Name");
                }else if(disease.getText().toString().isEmpty()) {
                    disease.setError("Please Enter Disease");
                }else if(medication.getText().toString().isEmpty()) {
                    medication.setError("Please Enter medication");
                }else {
                    //updating note
                    database_helper.updateNote(patient_name.getText().toString(), phone.getText().toString(),age.getText().toString(),arrivalDate.getText().toString(),disease.getText().toString(), medication.getText().toString(), arrayList.get(pos).getID());
                    arrayList.get(pos).setPatient_name(patient_name.getText().toString());
                    arrayList.get(pos).setDisease(disease.getText().toString());
                    arrayList.get(pos).setMedication(medication.getText().toString());
                    dialog.cancel();
                    //notify list
                    notifyDataSetChanged();
                }
            }
        });
    }
}