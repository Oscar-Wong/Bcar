package fypnctucs.bcar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamfu.wong on 28/9/2016.
 */

public class BLEClient {

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;

    private ScanCallback scanCallback;

    private boolean SCAN_STATUS = false;
    private MainActivity activity;

    protected BLEClient(MainActivity Activity) {
        activity = Activity;
        bluetoothManager = (BluetoothManager) Activity.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        bluetoothAdapter.setName("UVA Auto-Piloting");
    }

    public void BLEScan(boolean crl) {
        if (crl != SCAN_STATUS)
            if (crl) {

                activity.status("Start Scan");

                activity.clearScanList();
                SCAN_STATUS = true;

                ScanFilter.Builder filterBuilder = new ScanFilter.Builder();
                List<ScanFilter> filters = new ArrayList<>();
                filters.add(filterBuilder.build());

                ScanSettings settingsBuilder = new ScanSettings.Builder().build();

                bluetoothLeScanner.startScan(filters, settingsBuilder, scanCallback);

            } else {
                activity.status("Stop Scan");

                SCAN_STATUS = false;
                bluetoothLeScanner.stopScan(scanCallback);
            }
    }

    public void BLEScan() {
        BLEScan(!SCAN_STATUS);
    }

    public void SetScanCallback(ScanCallback callback) { scanCallback = callback; }

    public boolean isEnabled() {
        if (bluetoothAdapter != null)
            return bluetoothAdapter.isEnabled();
        else
            return false;
    }

    public void enable() {
        if (bluetoothAdapter != null)
            bluetoothAdapter.enable();
    }

    public void disable() {
        if (bluetoothAdapter != null)
            bluetoothAdapter.disable();
    }

}