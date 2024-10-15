package com.example.myapplication; // Adjust package name accordingly

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTaskTitle, editTextTaskDescription;
    private ListView listViewTasks;
    private Button buttonAddTask;

    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        listViewTasks = findViewById(R.id.listViewTasks);

        taskList = new ArrayList<>();
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listViewTasks.setAdapter(taskAdapter);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDeleteDialog(position);
            }
        });
    }

    private void addTask() {
        String title = editTextTaskTitle.getText().toString().trim();
        String description = editTextTaskDescription.getText().toString().trim();

        if (!title.isEmpty()) {
            taskList.add(new Task(title, description));
            taskAdapter.notifyDataSetChanged();
            editTextTaskTitle.setText("");
            editTextTaskDescription.setText("");
        } else {
            Toast.makeText(this, "Task title cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditDeleteDialog(int position) {
        Task selectedTask = taskList.get(position);
        String[] options = {"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(selectedTask.getTitle())
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            editTask(position);
                        } else {
                            deleteTask(position);
                        }
                    }
                });
        builder.show();
    }

    private void editTask(int position) {
        Task task = taskList.get(position);
        editTextTaskTitle.setText(task.getTitle());
        editTextTaskDescription.setText(task.getDescription());

        taskList.remove(position);
        taskAdapter.notifyDataSetChanged();
    }

    private void deleteTask(int position) {
        taskList.remove(position);
        taskAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
    }
}
