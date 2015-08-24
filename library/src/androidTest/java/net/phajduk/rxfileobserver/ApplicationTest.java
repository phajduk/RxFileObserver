/*
 * Copyright (C) 2015 Pawel Hajduk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.phajduk.rxfileobserver;

import android.os.Environment;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    public static final File SDCARD = Environment.getExternalStorageDirectory();
    private File fileToWatch;
    private Subscription subscription;

    @Before
    public void setUp() throws Exception {
        fileToWatch = new File(SDCARD, "file.test");
    }

    @After
    public void tearDown() throws Exception {
        subscription.unsubscribe();
        fileToWatch.delete();
    }

    @Test
    public void shouldEmitValuesAfterFileCreation() {
        // given
        final List<FileEvent> emittedEvents = new ArrayList<>();

        // when
        Observable<FileEvent> sdCardFileEvents = RxFileObserver.create(SDCARD);
        subscription = sdCardFileEvents.subscribe(new Action1<FileEvent>() {
            @Override
            public void call(FileEvent fileEvent) {
                if(fileToWatch.getAbsolutePath().endsWith(fileEvent.getPath())) {
                    emittedEvents.add(fileEvent);
                }
            }
        });


        createNewFile();
        waitInMilis(1000);

        // then
        assertThat(emittedEvents.size()).isEqualTo(3); //create, open, close_write
    }

    private void createNewFile() {
        try {
            boolean newFileCreated = fileToWatch.createNewFile();
            if(!newFileCreated) {
                throw new IOException("File already exist " + fileToWatch.getAbsolutePath());
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitInMilis(int time) {
        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}