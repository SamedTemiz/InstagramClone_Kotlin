package com.samedtemiz.instagramclone_kotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.samedtemiz.instagramclone_kotlin.R
import com.samedtemiz.instagramclone_kotlin.adapter.FeedRecyclerAdapter
import com.samedtemiz.instagramclone_kotlin.databinding.ActivityFeedBinding
import com.samedtemiz.instagramclone_kotlin.model.Post

class FeedActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFeedBinding

    //Firebase
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    //Objects
    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var feedAdapter : FeedRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = Firebase.firestore

        postArrayList = ArrayList<Post>()

        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedRecyclerAdapter(postArrayList)
        binding.recyclerView.adapter = feedAdapter
    }
    // Firestore get data
    private fun getData(){

        db.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener{ snapshot, error ->

            if(error != null){
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{

                if(snapshot != null){

                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents
                        postArrayList.clear()

                        for(document in documents){
                            //Casting
                            val comment = document.get("comment") as String
                            val email = document.get("email") as String
                            val downloadUrl = document.get("downloadUrl") as String
                            println(comment)
                            val post = Post(comment, email, downloadUrl)
                            postArrayList.add(post)
                        }

                        feedAdapter.notifyDataSetChanged()
                    }

                }

            }

        }

    }

    // Menüler
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.feed_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_post) {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)

        }else if(item.itemId == R.id.logout){
            auth.signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}