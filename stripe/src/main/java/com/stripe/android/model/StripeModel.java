package com.stripe.android.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Model for a Stripe API object.
 */
public abstract class StripeModel {

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(@Nullable Object obj);

    @NonNull
    static List<String> jsonArrayToList(@Nullable JSONArray jsonArray) {
        final List<String> list = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException ignored) {
                }
            }
        }

        return list;
    }
}
