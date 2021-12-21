/*
 * Copyright (c) 2021 VMware, Inc. or its affiliates, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reactor.netty.examples.documentation.http.client.read.timeout;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

public class Application {

	public static void main(String[] args) {
		HttpClient client =
				HttpClient.create()
				          .responseTimeout(Duration.ofSeconds(1));    //<1>

		String response1 =
				client.post()
				      .uri("https://example.com/")
				      .send((req, out) -> {
				          req.responseTimeout(Duration.ofSeconds(2)); //<2>
				          return out.sendString(Mono.just("body1"));
				      })
				      .responseContent()
				      .aggregate()
				      .asString()
				      .block();

		System.out.println("Response " + response1);

		String response2 =
				client.post()
				      .uri("https://example.com/")
				      .send((req, out) -> out.sendString(Mono.just("body2")))
				      .responseContent()
				      .aggregate()
				      .asString()
				      .block();

		System.out.println("Response " + response2);
	}
}