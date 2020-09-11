package com.miaoqi.database.lock.test;

import com.miaoqi.database.lock.pojo.Student;
import com.miaoqi.database.lock.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        // test1();
        // test2();
        // test3();
        // test4();
        // test5();
        // test6();
        test7();
    }


    /**
     * 事物中对主键列操作
     * A: 对 id = 1 加排它锁
     * B: 对 id = 1 加排它锁
     * C: 对 id = 1 加共享锁
     * D: 对 id = 2 加排它锁
     * 结论: B 被阻塞, C 被阻塞, D 没被阻塞, 行锁生效, 排它锁不能与排它锁和共享锁共存
     *
     * @author miaoqi
     * @date 2019-07-08
     * @param
     * @return
     */
    public static void test1() throws Exception {
        new Thread(Main::test1_A).start();
        Thread.sleep(1000L);
        new Thread(Main::test1_B).start();
        new Thread(Main::test1_C).start();
        new Thread(Main::test1_D).start();
    }

    private static void test1_A() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM tb_student WHERE id = 1 FOR UPDATE";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "A");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            st = conn.prepareStatement("SELECT sleep(5) FROM dual");
            st.executeQuery();
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test1_B() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE id = 1 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "B");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test1_C() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE id = 1 LOCK IN SHARE MODE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "C");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test1_D() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE id = 2 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "D");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }


    /**
     * 事物中对主键列操作
     * A: 对 id = 1 加共享锁
     * B: 对 id = 1 加排它锁
     * C: 对 id = 1 加共享锁
     * D: 对 id = 2 加排它锁
     * 结论: B 被阻塞, C 没被阻塞, D 没被阻塞, 行锁生效, 共享锁可以与共享锁并存不能与排它锁共存
     *
     * @author miaoqi
     * @date 2019-07-08
     * @param
     * @return
     */
    public static void test2() throws Exception {
        new Thread(Main::test2_A).start();
        Thread.sleep(1000L);
        new Thread(Main::test2_B).start();
        new Thread(Main::test2_C).start();
        new Thread(Main::test2_D).start();
    }

    private static void test2_A() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM tb_student WHERE id = 1 LOCK IN SHARE MODE";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "A");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            st = conn.prepareStatement("SELECT sleep(5) FROM dual");
            st.executeQuery();
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test2_B() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE id = 1 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "B");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test2_C() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE id = 2 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "C");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test2_D() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE id = 2 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "D");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    /**
     * 非事物中对主键列操作
     * A: 对 id = 1 加排它锁
     * B: 对 id = 1 加排它锁
     * C: 对 id = 1 加共享锁
     * D: 对 id = 2 加排它锁
     * 结论: B 没被阻塞, C 没被阻塞, D 没被阻塞, 行锁失效, 行锁需要在事物中执行
     *
     * @author miaoqi
     * @date 2019-07-08
     * @param
     * @return
     */
    public static void test3() throws Exception {
        new Thread(Main::test3_A).start();
        Thread.sleep(1000L);
        new Thread(Main::test3_B).start();
        new Thread(Main::test3_C).start();
        new Thread(Main::test3_D).start();
    }

    private static void test3_A() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            // conn.setAutoCommit(false);
            String sql = "SELECT * FROM tb_student WHERE id = 1 FOR UPDATE";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "A");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            st = conn.prepareStatement("SELECT sleep(5) FROM dual");
            st.executeQuery();
            // conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test3_B() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql1 = "SELECT * FROM tb_student WHERE id = 1 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "B");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test3_C() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql1 = "SELECT * FROM tb_student WHERE id = 1 LOCK IN SHARE MODE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "C");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test3_D() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql1 = "SELECT * FROM tb_student WHERE id = 2 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "D");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    /**
     * 事物中对非索引列操作
     * A: 对 name = miaomiao1 加排它锁
     * B: 对 name = miaomiao1 加排它锁
     * C: 对 name = miaomiao1 加共享锁
     * D: 对 name = miaomiao2 加排它锁
     * 结论: B 被阻塞, C 被阻塞, D 被阻塞, 行锁失效, 表锁生效, 在事物中对一个非索引列加锁会发生表锁
     *
     * @author miaoqi
     * @date 2019-07-08
     * @param
     * @return
     */
    public static void test4() throws Exception {
        new Thread(Main::test4_A).start();
        Thread.sleep(1000L);
        new Thread(Main::test4_B).start();
        new Thread(Main::test4_C).start();
        new Thread(Main::test4_D).start();
    }

    private static void test4_A() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM tb_student WHERE name = 'miaomiao1' FOR UPDATE";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "A");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            st = conn.prepareStatement("SELECT sleep(5) FROM dual");
            st.executeQuery();
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test4_B() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE name = 'miaomiao1' FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "B");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test4_C() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE name = 'miaomiao1' LOCK IN SHARE MODE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "C");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test4_D() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE name = 'miaomiao2' FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "D");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }


    /**
     * 事物中对非索引列操作
     * A: 对 name = miaomiao1 加共享锁
     * B: 对 name = miaomiao1 加排它锁
     * C: 对 name = miaomiao1 加共享锁
     * D: 对 name = miaomiao2 加排它锁
     * 结论: B 被阻塞, C 被阻塞, D 被阻塞, 行锁失效, 表锁生效, 在事物中对一个非索引列加锁会发生表锁
     *
     * @author miaoqi
     * @date 2019-07-08
     * @param
     * @return
     */
    public static void test5() throws Exception {
        new Thread(Main::test5_A).start();
        Thread.sleep(1000L);
        new Thread(Main::test5_B).start();
        new Thread(Main::test5_C).start();
        new Thread(Main::test5_D).start();
    }

    private static void test5_A() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM tb_student WHERE name = 'miaomiao1' LOCK IN SHARE MODE";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "A");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            st = conn.prepareStatement("SELECT sleep(5) FROM dual");
            st.executeQuery();
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test5_B() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE name = 'miaomiao1' FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "B");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test5_C() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE name = 'miaomiao1' LOCK IN SHARE MODE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "C");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test5_D() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE name = 'miaomiao2' FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "D");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }


    /**
     * 事物中对非唯一索引列值不唯一操作
     * A: 对 age != 1 加共享锁
     * B: 对 age = 1 加排它锁
     * C: 对 age = 1 加共享锁
     * D: 对 age = 20 加排它锁
     * 结论: B 被阻塞, C 被阻塞, D 被阻塞, 行锁生效, 表锁生效, 当索引列模糊匹配时会发生表锁
     *
     * @author miaoqi
     * @date 2019-07-08
     * @param
     * @return
     */
    public static void test6() throws Exception {
        new Thread(Main::test6_A).start();
        Thread.sleep(1000L);
        new Thread(Main::test6_B).start();
        new Thread(Main::test6_C).start();
        new Thread(Main::test6_D).start();
    }

    private static void test6_A() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM tb_student WHERE age != 1 LOCK IN SHARE MODE";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "A");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            st = conn.prepareStatement("SELECT sleep(5) FROM dual");
            st.executeQuery();
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test6_B() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE age = 1 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "B");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test6_C() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE age = 1 LOCK IN SHARE MODE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "C");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test6_D() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE age = 20 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "D");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    /**
     * 事物中对非唯一索引列值不唯一操作
     * A: 对 age = 1 加排它锁
     * B: 对 age = 1 加排它锁
     * C: 对 age = 1 加共享锁
     * D: 对 age = 20 加排它锁
     * 结论: B 被阻塞, C 没被阻塞, D 没被阻塞, 行锁生效, 在事物中对一个非唯一索引列加锁会触发行锁
     *
     * @author miaoqi
     * @date 2019-07-08
     * @param
     * @return
     */
    public static void test7() throws Exception {
        new Thread(Main::test7_A).start();
        Thread.sleep(1000L);
        new Thread(Main::test7_B).start();
        new Thread(Main::test7_C).start();
        new Thread(Main::test7_D).start();
    }

    private static void test7_A() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM tb_student WHERE age = 1 FOR UPDATE";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "A");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            st = conn.prepareStatement("SELECT sleep(5) FROM dual");
            st.executeQuery();
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test7_B() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE age = 1 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "B");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test7_C() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE age = 1 LOCK IN SHARE MODE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "C");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    private static void test7_D() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "SELECT * FROM tb_student WHERE age = 20 FOR UPDATE";
            st = conn.prepareStatement(sql1);
            rs = st.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name") + "D");
                student.setAge(rs.getInt("age"));
                student.setIdcard(rs.getString("idcard"));
                students.add(student);
            }
            students.forEach(System.out::println);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

}
