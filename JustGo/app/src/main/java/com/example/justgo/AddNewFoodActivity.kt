package com.example.justgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.Food
import com.example.justgo.Entitys.FoodType
import com.example.justgo.Entitys.Trip
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewFoodActivity : AppCompatActivity() {

    private lateinit var food_dropdown : Spinner
    private lateinit var trip : Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_food)

        trip = intent.getSerializableExtra("trip") as Trip

        food_dropdown = findViewById(R.id.foodType_dorpdown)
        food_dropdown.adapter = ArrayAdapter<FoodType>(this, android.R.layout.simple_spinner_item, FoodType.values())

        val discard : FloatingActionButton
        discard=findViewById(R.id.discardFood_floatActionButton)
        discard.setOnClickListener {
            val intent = Intent(this, FoodsActivity::class.java)
            intent.putExtra("trip", trip)
            startActivity(intent)
        }
        val save : FloatingActionButton
        save=findViewById(R.id.saveFood_floatActionButton)
        save.setOnClickListener {
            val name: EditText
            name=findViewById(R.id.activityName_EditText)
            var location: EditText
            location = findViewById(R.id.activityDescription_EditText)

            if(!(name.text.toString().equals("")) && !(location.text.toString().equals(""))) {

                trip.addFood(name.text.toString(), location.text.toString(), food_dropdown.selectedItem as FoodType)
                var foodDatabaseHelper = DatabaseHelper(this)

                TripManager.replaceTrip(
                    TripManager.getTripbyName(trip.nameofTrip).first(),
                    trip
                )
                foodDatabaseHelper.addFood(Food(name.text.toString(), location.text.toString(), food_dropdown.selectedItem as FoodType),trip)
                val intent = Intent(this, FoodsActivity::class.java)
                intent.putExtra("trip", trip)
                startActivity(intent)

            }
        }
    }
}