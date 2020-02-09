package epam.by.Auction.connection;

import epam.by.Auction.exception.NoConnectionException;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private Queue<ProxyConnection> availableConnections;
    private Queue<ProxyConnection> connectionInUse;

    public static ConnectionPool instance;
    private static Lock instanceLock = new ReentrantLock();
    private static Lock returnConnectionLock = new ReentrantLock();
    private static Lock getConnectionLock = new ReentrantLock();
    private final static int INITIAL_POOL_SIZE = 10;
    private final Semaphore semaphore = new Semaphore(INITIAL_POOL_SIZE, true);
    private final static long TIMEOUT = 1;

    private ConnectionPool () {
        availableConnections = new ArrayDeque<>();
        connectionInUse = new ArrayDeque<>();
        createPool();
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instanceLock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    public void createPool() {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            availableConnections.offer(getConnection());
        }
    }

    public ProxyConnection getConnectionFromPool() {
        getConnectionLock.lock();
        ProxyConnection proxyConnection = null;
        try {
            semaphore.tryAcquire(TIMEOUT, TimeUnit.HOURS);
            proxyConnection = availableConnections.poll();
            connectionInUse.offer(proxyConnection);
        } catch (InterruptedException e) {
            throw new NoConnectionException(e);
        } finally {
            getConnectionLock.unlock();
        }
        return proxyConnection;
    }

    public void returnConnection(ProxyConnection proxyConnection) {
        returnConnectionLock.lock();
        semaphore.release();
        try {
            if (connectionInUse.contains(proxyConnection)) {
                availableConnections.offer(proxyConnection);
                connectionInUse.remove(proxyConnection);
            }
        } finally {
            returnConnectionLock.unlock();
        }
    }

    private ProxyConnection getConnection() {
        return ConnectionFactory.create(this);
    }

}