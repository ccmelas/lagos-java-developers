package com.melas.javadevelopers_lagos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.melas.javadevelopers_lagos.models.Developer;
import com.melas.javadevelopers_lagos.utilities.CircleTransform;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private Button shareButton;
    private Developer developer;
    private View.OnClickListener shareButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            shareProfile();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle bundle = getIntent().getExtras();
        developer = bundle.getParcelable(MainActivity.DATA_DEVELOPER);
        TextView usernameTextView = (TextView) findViewById(R.id.username);
        TextView githubUrlTextView = (TextView) findViewById(R.id.github_url);
        ImageView avatarView = (ImageView) findViewById(R.id.avatar);
        shareButton = (Button) findViewById(R.id.share_profile);

        usernameTextView.setText(developer.getUsername());
        githubUrlTextView.setText(developer.getGithubURL());
        Picasso.with(this).load(developer.getAvatar()).transform(new CircleTransform()).into(avatarView);
        shareButton.setOnClickListener(shareButtonClicked);
    }

    private void shareProfile() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        share.putExtra(Intent.EXTRA_SUBJECT, "Java Developers in Lagos");
        share.putExtra(Intent.EXTRA_TEXT, developer.getGithubURL());
        startActivity(Intent.createChooser(share, "Share " + developer.getUsername() + " 's profile"));
    }
}
