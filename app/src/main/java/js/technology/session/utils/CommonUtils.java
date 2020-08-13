package js.technology.session.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class CommonUtils {

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        Log.d("TAG", "loadJSONFromAsset: " + new String(buffer, StandardCharsets.UTF_8));

        return new String(buffer, StandardCharsets.UTF_8);
    }
}
