package com.ci.urlfetch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FetchTravis {
	public boolean isBuildSuccess() {
		try {
			URL url = new URL("https://api.travis-ci.com/repositories/dextra/portal_documentos_plataforma/cc.json?token=6GHpRBf2tzsQJpzrmA7U&branch=master");
			Object content = url.getContent();

			InputStreamReader reader = new InputStreamReader((InputStream) content);
			BufferedReader in = new BufferedReader(reader);

			JsonObject json = new JsonParser().parse(in).getAsJsonObject();
			JsonElement jsonResult = json.get("last_build_result");
			JsonElement jsonFinishedTime = json.get("last_build_finished_at");

			if (!JsonNull.class.equals(jsonFinishedTime.getClass())) {
				if (JsonNull.class.equals(jsonResult.getClass())) {
					return false;
				}
				return jsonResult.getAsInt() == 0;
			}
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
