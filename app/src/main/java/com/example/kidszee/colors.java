package com.example.kidszee;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class colors extends AppCompatActivity {

    // Define both light and dark colors
        private static final int[] LIGHT_COLORS = {
                Color.rgb(255, 200, 200), // Light Red
                Color.rgb(255, 255, 200), // Light Yellow
                Color.rgb(200, 255, 200), // Light Green
                Color.rgb(200, 200, 255), // Light Blue
                Color.rgb(255, 200, 255), // Light Magenta
                Color.rgb(200, 255, 255), // Light Cyan
                Color.rgb(255, 225, 200), // Light Orange
                Color.rgb(225, 200, 255)  // Light Purple
        };

        private static final int[] DARK_COLORS = {
                Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE,
                Color.MAGENTA, Color.CYAN, Color.rgb(255, 165, 0), // Orange
                Color.rgb(128, 0, 128)    // Purple
        };

        private static final String[] COLOR_NAMES = {
                "Red", "Yellow", "Green", "Blue",
                "Pink", "Light Blue", "Orange", "Purple"
        };

        private List<ImageView> colorTargets;
        private List<TextView> targetLabels;
        private List<ImageView> draggableColors;
        private List<TextView> draggableLabels;
        private int currentDragColor;
        private int currentDragIndex;
        private int score = 0;
        private TextView scoreTextView;
        private Map<Integer, Boolean> completedMatches;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_colors);

            scoreTextView = findViewById(R.id.scoreTextView);
            completedMatches = new HashMap<>();

            setupColorTargets();
            setupDraggableColors();
        }

        private void setupColorTargets() {
            colorTargets = new ArrayList<>();
            targetLabels = new ArrayList<>();

            // Add your target ImageViews and their corresponding TextViews from layout
            colorTargets.add(findViewById(R.id.targetRed));
            colorTargets.add(findViewById(R.id.targetYellow));
            colorTargets.add(findViewById(R.id.targetGreen));
            colorTargets.add(findViewById(R.id.targetBlue));

            targetLabels.add(findViewById(R.id.targetRedLabel));
            targetLabels.add(findViewById(R.id.targetYellowLabel));
            targetLabels.add(findViewById(R.id.targetGreenLabel));
            targetLabels.add(findViewById(R.id.targetBlueLabel));

            // Set up targets and their labels
            for (int i = 0; i < colorTargets.size(); i++) {
                ImageView target = colorTargets.get(i);
                TextView label = targetLabels.get(i);
                final int colorIndex = i;

                // Set light background color for the target initially
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setColor(LIGHT_COLORS[colorIndex]);
                shape.setStroke(5, Color.DKGRAY);
                target.setBackground(shape);

                // Set the label text
                label.setText(COLOR_NAMES[colorIndex]);

                // Set up drop listener
                target.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        switch (event.getAction()) {
                            case DragEvent.ACTION_DRAG_ENTERED:
                                // Highlight the target when drag enters
                                if (currentDragIndex == colorIndex && !completedMatches.containsKey(colorIndex)) {
                                    GradientDrawable highlight = new GradientDrawable();
                                    highlight.setShape(GradientDrawable.OVAL);
                                    highlight.setColor(Color.parseColor("#E0E0E0")); // Light highlight
                                    highlight.setStroke(5, Color.BLACK);
                                    v.setBackground(highlight);
                                }
                                return true;

                            case DragEvent.ACTION_DRAG_EXITED:
                                // Reset the target when drag exits
                                if (!completedMatches.containsKey(colorIndex)) {
                                    GradientDrawable normal = new GradientDrawable();
                                    normal.setShape(GradientDrawable.OVAL);
                                    normal.setColor(LIGHT_COLORS[colorIndex]);
                                    normal.setStroke(5, Color.DKGRAY);
                                    v.setBackground(normal);
                                }
                                return true;

                            case DragEvent.ACTION_DROP:
                                // Check if the dragged color matches the target
                                if (currentDragIndex == colorIndex) {
                                    // Correct match - change to dark color
                                    GradientDrawable darkShape = new GradientDrawable();
                                    darkShape.setShape(GradientDrawable.OVAL);
                                    darkShape.setColor(DARK_COLORS[colorIndex]);
                                    darkShape.setStroke(5, Color.BLACK);
                                    v.setBackground(darkShape);

                                    // Mark as completed
                                    completedMatches.put(colorIndex, true);

                                    // Make the draggable "disappear" (or dim significantly)
                                    ImageView draggable = draggableColors.get(colorIndex);
                                    TextView draggableLabel = draggableLabels.get(colorIndex);
                                    draggable.setAlpha(0.3f);
                                    draggableLabel.setAlpha(0.3f);

                                    // Show toast message for successful match
                                    Toast.makeText(colors.this, "Good job!", Toast.LENGTH_SHORT).show();

                                    playCorrectAnimation(target);
                                    playCorrectAnimation(label);
                                    updateScore(true);
                                } else {
                                    // Incorrect match
                                    if (!completedMatches.containsKey(colorIndex)) {
                                        GradientDrawable normal = new GradientDrawable();
                                        normal.setShape(GradientDrawable.OVAL);
                                        normal.setColor(LIGHT_COLORS[colorIndex]);
                                        normal.setStroke(5, Color.DKGRAY);
                                        v.setBackground(normal);
                                    }


                                    updateScore(false);
                                }
                                return true;
                        }
                        return true;
                    }
                });
            }
        }

        private void setupDraggableColors() {
            draggableColors = new ArrayList<>();
            draggableLabels = new ArrayList<>();

            // Add your draggable ImageViews and their corresponding TextViews from layout
            draggableColors.add(findViewById(R.id.draggableRed));
            draggableColors.add(findViewById(R.id.draggableYellow));
            draggableColors.add(findViewById(R.id.draggableGreen));
            draggableColors.add(findViewById(R.id.draggableBlue));

            draggableLabels.add(findViewById(R.id.draggableRedLabel));
            draggableLabels.add(findViewById(R.id.draggableYellowLabel));
            draggableLabels.add(findViewById(R.id.draggableGreenLabel));
            draggableLabels.add(findViewById(R.id.draggableBlueLabel));

            // Set up draggables and their labels
            for (int i = 0; i < draggableColors.size(); i++) {
                ImageView draggable = draggableColors.get(i);
                TextView label = draggableLabels.get(i);
                final int colorIndex = i;

                // Set light background color
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setColor(LIGHT_COLORS[colorIndex]);
                shape.setStroke(5, Color.DKGRAY);
                draggable.setBackground(shape);

                // Set the label text
                label.setText(COLOR_NAMES[colorIndex]);

                // Set up touch listener to start drag
                draggable.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN &&
                                !completedMatches.containsKey(colorIndex)) {

                            ClipData data = ClipData.newPlainText("color", String.valueOf(colorIndex));
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                v.startDragAndDrop(data, shadowBuilder, v, 0);
                            } else {
                                v.startDrag(data, shadowBuilder, v, 0);
                            }

                            currentDragIndex = colorIndex;
                            currentDragColor = DARK_COLORS[colorIndex];
                            return true;
                        }
                        return false;
                    }
                });
            }
        }

        private void playCorrectAnimation(View view) {
            view.animate()
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(300)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            view.animate()
                                    .scaleX(1.0f)
                                    .scaleY(1.0f)
                                    .setDuration(300);
                        }
                    });
        }


        private void updateScore(boolean correct) {
            if (correct) {
                score += 10;

                // Check if all matches are complete
                if (completedMatches.size() >= colorTargets.size()) {
                    showCompletionMessage();
                }
            } else {
                // Optional: deduct points for incorrect matches
                // score -= 5;
                // score = Math.max(0, score); // Don't go below zero
            }
            scoreTextView.setText("Score: " + score);
        }

        private void showCompletionMessage() {
            // Create a congratulations dialog or animation
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Great Job!");
            builder.setMessage("You've matched all the colors! Final score: " + score);
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    resetGame();
                }
            });
            builder.setNegativeButton("Next Activity", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Move to next activity if you have one
                    //Intent intent = new Intent(colors.this, poems.class);
                    // startActivity(intent);
                }
            });
            builder.setCancelable(false);
            builder.show();
        }

        private void resetGame() {
            // Reset score
            score = 0;
            scoreTextView.setText("Score: " + score);

            // Clear completed matches
            completedMatches.clear();

            // Reset targets to light colors
            for (int i = 0; i < colorTargets.size(); i++) {
                ImageView target = colorTargets.get(i);
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setColor(LIGHT_COLORS[i]);
                shape.setStroke(5, Color.DKGRAY);
                target.setBackground(shape);
            }

            // Reset draggables
            for (int i = 0; i < draggableColors.size(); i++) {
                ImageView draggable = draggableColors.get(i);
                TextView label = draggableLabels.get(i);

                // Reset alpha
                draggable.setAlpha(1.0f);
                label.setAlpha(1.0f);

                // Reset color
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setColor(LIGHT_COLORS[i]);
                shape.setStroke(5, Color.DKGRAY);
                draggable.setBackground(shape);
            }
        }
    }