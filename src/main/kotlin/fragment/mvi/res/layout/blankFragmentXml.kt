package fragment.mvi.res.layout

fun blankFragmentXml(
        fragmentClass: String,
        viewModelClass: String,
        packageName: String
) = """<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="viewmodel"
            type="${packageName}.${viewModelClass}" />

        <import type="android.view.View" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="${packageName}.${fragmentClass}">

        <com.google.android.material.button.MaterialButton
            android:id="@+id/button"
            android:layout_width="200dp"
            android:layout_height="wrap_content"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            tools:text="Disable Me"
            android:enabled="@{viewmodel.state.buttonActive}"
            />
        
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>

"""