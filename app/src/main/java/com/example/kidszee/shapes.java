package com.example.kidszee;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class shapes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes);

        // Set up draggable shapes
        setupDraggableShape(R.id.triangleShape);
        setupDraggableShape(R.id.hexagonShape);
        setupDraggableShape(R.id.circleShape);
        setupDraggableShape(R.id.rectangleShape);

        // Set up drop target areas
        setupDropTarget(R.id.squareTarget, "rectangle");
        setupDropTarget(R.id.triangleTarget, "triangle");
        setupDropTarget(R.id.hexagonTarget, "hexagon");
        setupDropTarget(R.id.circleTarget, "circle");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupDraggableShape(int shapeId) {
        ImageView shapeView = findViewById(shapeId);

        shapeView.setOnTouchListener(new View.OnTouchListener()
        {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    ClipData clipData = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(clipData, shadowBuilder, v, 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void setupDropTarget(int targetId, String expectedShape) {
        FrameLayout targetView = findViewById(targetId);

        targetView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                    case DragEvent.ACTION_DRAG_ENTERED:
                    case DragEvent.ACTION_DRAG_LOCATION:
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;

                    case DragEvent.ACTION_DROP:
                        View draggedView = (View) event.getLocalState();
                        String shapeTag = draggedView.getTag().toString();

                        if (shapeTag.equals(expectedShape)) {
                            // Correct match
                            ViewGroup owner = (ViewGroup) draggedView.getParent();
                            owner.removeView(draggedView);

                            // Add to target
                            FrameLayout container = (FrameLayout) v;
                            container.addView(draggedView);

                            // Center in new container
                            draggedView.setLayoutParams(new FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));

                            // Show success feedback
                            Toast.makeText(shapes.this,
                                    "Good job!", Toast.LENGTH_SHORT).show();

                            // Disable further interaction with this shape
                            draggedView.setOnTouchListener(null);

                            // Check if all shapes are matched
                            checkAllMatched();
                        } else {
                            // Incorrect match
                            Toast.makeText(shapes.this,
                                    "Try again!", Toast.LENGTH_SHORT).show();
                            draggedView.setVisibility(View.VISIBLE);
                        }
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void checkAllMatched() {
        // Check if all target areas contain shapes
        FrameLayout squareTarget = findViewById(R.id.squareTarget);
        FrameLayout triangleTarget = findViewById(R.id.triangleTarget);
        FrameLayout hexagonTarget = findViewById(R.id.hexagonTarget);
        FrameLayout circleTarget = findViewById(R.id.circleTarget);

        if (squareTarget.getChildCount() > 0 &&
                triangleTarget.getChildCount() > 0 &&
                hexagonTarget.getChildCount() > 0 &&
                circleTarget.getChildCount() > 0) {

            Toast.makeText(this, "Congratulations! You matched all shapes correctly!",
                    Toast.LENGTH_LONG).show();

            // Here you could add code to proceed to the next level or activity
        }
    }
}