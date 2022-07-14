package ua.edu.sumdu.j2se.kulykov.tasks.models;

import java.io.*;
import java.time.*;
import java.util.TimeZone;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class TaskIO {

    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public LocalDateTime read(JsonReader reader) throws IOException {
            reader.beginObject();
            long timeMilliSec = 0;
            String fieldName = null;
            while (reader.hasNext()) {
                fieldName = reader.nextName();
                timeMilliSec = reader.nextLong();
            }
            reader.endObject();

            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMilliSec),
                    TimeZone.getDefault().toZoneId());
        }

        @Override
        public void write(JsonWriter writer, LocalDateTime time) throws IOException {
            writer.beginObject();
            writer.name("timeMilliSec");
            writer.value(dateToNumber(time));
            writer.endObject();
        }
    }

    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            dos.writeInt(tasks.size());
            for (Task task : tasks) {
                dos.writeInt(task.getTitle().length());
                dos.writeChars(task.getTitle());
                dos.writeBoolean(task.isActive());
                dos.writeInt(task.getRepeatInterval());
                if (task.isRepeated()) {
                    long start = dateToNumber(task.getStartTime());
                    long end = dateToNumber(task.getEndTime());
                    dos.writeLong(start);
                    dos.writeLong(end);
                }
                else {
                    long time = dateToNumber(task.getTime());
                    dos.writeLong(time);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (DataInputStream dis = new DataInputStream(in)) {
            int taskAmount = dis.readInt();
            for (int i = 0; i < taskAmount; i++) {
                Task task;
                int length = dis.readInt();

                char[] cTitle = new char[length];
                for (int j = 0; j < length; j++)
                    cTitle[j] = dis.readChar();

                String title = String.valueOf(cTitle);

                boolean isActive = dis.readBoolean();
                int time_interval_repeat = dis.readInt();
                if (time_interval_repeat > 0) {
                    long startMSec = dis.readLong();
                    long endMSec = dis.readLong();
                    LocalDateTime time_start = numberToDate(startMSec);
                    LocalDateTime time_end = numberToDate(endMSec);
                    task = new Task(title, time_start, time_end, time_interval_repeat);
                } else {
                    long timeMSec = dis.readLong();
                    LocalDateTime time = numberToDate(timeMSec);
                    task = new Task(title, time);
                }

                task.setActive(isActive);
                tasks.add(task);
            }
        } catch (EOFException e) {
            System.out.println("No tasks found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            write(tasks, bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            read(tasks, bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        try {
            GsonBuilder gBuilder = new GsonBuilder();
            gBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
            Gson gson = gBuilder.create();
            out.write(gson.toJson(tasks.getStream().toArray()));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        try (BufferedReader br = new BufferedReader(in)) {
            StringBuilder strBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null)
                strBuilder.append(str);

            GsonBuilder gBuilder = new GsonBuilder();
            gBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
            Gson gson = gBuilder.create();

            Task[] atl = gson.fromJson(strBuilder.toString(), Task[].class);

            for (Task task : atl) {
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            write(tasks, bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(AbstractTaskList tasks, File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            read(tasks, br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long dateToNumber(LocalDateTime time) {
        ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    private static LocalDateTime numberToDate(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                TimeZone.getDefault().toZoneId());
    }
}