package com.example.shoppinglist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    var isEditing: Boolean = false
)


@Composable
fun ShoppingList() {


    /*
    //  this line creates a variable named sItems that represents a list of ShoppingItems.
    //  The mutableStateOf function is used to make the list mutable, meaning it can be changed later.
    //  The remember function is used to ensure that the list retains its state even if the
    //  user interacts with the app or the screen changes.



    // This sItems is a state list
    // Ihis list uses mutable state of, it will automatically update our user interface as soon as
    // the items inside of that list changes or gets added/removed

 */

    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            /*
            // Example of Lambda
            // val doubleNumber = (Int) -> Int = { it * 2 }  Here inside Bracket "Int" is our input type
            // after arrow, that "Int" is our output type and inside curly brackets we wrote our logic or calculation
             */

            // Button text
            Text(text = "Add Item")
        }
        LazyColumn(
            // using the below line, Lazy column is taking the entire space(including height and width)
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ){
            // setting up the content for our Lazy column using items property
            items(sItems) {


                // We can reuse this code whenever we want to modify items inside of a list
                    item ->
                if (item.isEditing) {
                    ShoppingItemEditor(item = item, onEditComplete = { editedname, editedquantity ->
                        sItems = sItems.map { it.copy(isEditing = false) }
                        val editedItem =
                            sItems.find { it.id == item.id } // this find function is used to find the item inside a list
                        editedItem?.let {
                            it.name = editedname
                            it.quantity = editedquantity
                        }
                    })
                } else {
                    ShoppingListItem(item = item, onEditClick = {
                        // finding out which item we are editing and changing is "isEditing" to true
                        sItems = sItems.map { it.copy(isEditing = (it.id == item.id)) }

                    },
                        onDeleteClick = {
                            sItems = sItems - item
                        })
                }
            }
        }





        // Using the Alert Dialer to add items in the Shopping list
        if (showDialog){
            AlertDialog(onDismissRequest = { showDialog = false  },
                confirmButton = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                    // this SpaceBetween will create the space between the two Buttons

                                ){
                                    Button(onClick = {
                                        if(itemName.isNotBlank()){
                                            val newItem = ShoppingItem(
                                                id = sItems.size + 1,
                                                name = itemName,
                                                quantity = itemQuantity.toInt()
                                            )
                                            sItems = sItems + newItem
                                            itemName = ""
                                            itemQuantity = ""
                                            showDialog = false
                                        }
                                    }) {
                                        Text(text = "Add")
                                    }
                                    Button(onClick = {showDialog = false}) {
                                        Text(text = "Cancel")

                                    }
                                }
                },

                // Customizing our alert dialog

                title = { Text(text = "Add Shopping Items") },
                text = {
                    Column() {
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )

                        OutlinedTextField(
                            value = itemQuantity,
                            onValueChange = { itemQuantity = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }

            )



            }
        }
}
// Here we will create the logic of our edit button
@Composable
fun ShoppingItemEditor(item: ShoppingItem, onEditComplete: (String,Int) -> Unit){
    var editedName by remember{ mutableStateOf(item.name) }
    var editedQuantity by remember{ mutableStateOf(item.quantity.toString()) }
    var isEditing by remember{ mutableStateOf(item.isEditing) }


    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Column {
            BasicTextField(value = editedName,
                onValueChange = {editedName = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
                // This wrapContentSize says that only take as much space as the items that are inside of you actually need
            )

            BasicTextField(value = editedQuantity,
                onValueChange = {editedQuantity = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }

        Button(
            onClick = {
                onEditComplete(editedName, editedQuantity.toIntOrNull()?: 1)
                isEditing = false
            }) {
                Text("Save")
            }

        }
    }



/*
-----Examples of Maps-----

// This is an example of a map
val myMap = mapOf(
    "key1" to "value1",
    "key2" to "value2"
)

// This is an example of a mutable map
val myMutableMap = mutableMapOf(
    "key1" to "value1",
    "key2" to "value2"
)

// This is an example of a list of maps
val myListOfMaps = listOf(
    mapOf(
        "key1" to "value1",
        "key2" to "value2"
    ),
    )
)
// Code

val numbers = listof(1,2,3)
val doubled = numbers.map { it * 2} // this map transforms the entire list by iterating through each item of the list, inside {}, I used what I want to do with this map
println(doubled)

// Output

[2,4,6]

// what this does is, it went to each item of the list and doubled it, basically it maps each of those items by multiplier of 2

 */


/*
USE OF COPY KEYWORD

val blueRoseVase = Vase(color = "Blue", design = "Rose")
val redFlowerVase = blueRoseVase.copy(color = "Red", design = "Flower")

// Here I copied the original blueRoseVase and changed the color and design
// copy keyword basically copies the original one and then we can use that copy anywhere without changing the original one


// data class
data class Vase(val color: String, val design: String)

 */


/*
// LET AND NULLABLE



let allows you to use and transform an object without having to check if it is null or not

In Kotlin, `let` and `nullable` are concepts related to handling nullable types.

1. **Nullable Types:**
   In Kotlin, a variable can be declared as nullable by appending a `?` to its type. For example:

   var name: String? = "John" // This "?" says that this variable can be null or it can be a String

  // Here, `name` can hold a String or a `null` value.

2. **`let` Function:**
   The `let` function is used to perform operations on a nullable object without the need for explicit null checks.
   It is often used in combination with the safe call operator `?.` to invoke a block of code only if the object is not null.

   Here's an example:

   // code
   var name: String? = "John"

   name?.let {
       println("Length of name: ${it.length}")
   }

   In this example, the `let` function is called only if `name` is not null,
   allowing safe access to its properties or methods inside the lambda block.

In simple terms, nullable types allow variables to hold either a regular value or a special value `null`.
The `let` function is a convenient way to work with nullable objects without worrying about null pointer exceptions.



 */





@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit


    // Here on Edit and delete click is a lambda function,
    // This lambda function doesn't take any parameters and returns nothing
    // what it means is, we can pass a function in these parameters of onEditClick and onDeleteClick
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, color = Color.Blue),
                shape = RectangleShape
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Text(text = "Qty: ${item.quantity}", modifier = Modifier.padding(8.dp))
        Row(modifier = Modifier.padding(8.dp)){
            // This IconButton is simply a button, but its appearance is like an icon
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }

            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }

}


