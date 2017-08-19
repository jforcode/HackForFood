package com.example.jeevan.hackforfood.interfaces;

import android.os.Bundle;

/**
 * Created by jeevan on 7/16/17.
 * any child who wants to update the parent should use this interface
 */

public interface UpdateParentInterface {
    void update(String action, Bundle bundle);
}
