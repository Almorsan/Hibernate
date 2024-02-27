package miEmpresa2;

import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import antlr.collections.List;

public class HibernateEnterprise {
	private static SessionFactory sf; // this SessionFactory will be created once and used for all the connections

	HibernateEnterprise() {// constructor
		// sf = HibernateUtil.getSessionFactory();
		sf = new Configuration().configure().buildSessionFactory(); // also works
	}

	public void addProduct(int id, String nombre, double precio) {
		Session session = sf.openSession();// session es l variable que tiene el método
		// save para guardar productos
		Transaction tx = null;
		// create the product with the parameters in the method
		Productos p = new Productos();
		p.setNombre(nombre);
		p.setPrecio(precio);
		p.setId(id);
		// keep it in the database=🡺session.save(p)
		try {
			System.out.printf("Inserting a row in the database: %s, %s, %s\n", id, nombre, precio);
			tx = session.beginTransaction();
			session.save(p);// we INSERT p into the table PRODUCTS
			tx.commit();// if session.save doesn't produce an exception, we "commit" the transaction
		} catch (Exception e) {// if there is any exception, we "rollback" and close safely
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
	}

	public void mostrarProductos() {
		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			java.util.List listaProductos = session.createQuery("FROM Productos").list();
			Iterator iterator = listaProductos.iterator();

			while (iterator.hasNext()) {
				Productos p = (Productos) iterator.next();
				System.out.println("ID: " + p.getId());
				System.out.println("Nombre: " + p.getNombre());
				System.out.println("Precio: " + p.getPrecio());
			}
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();
		}
	}

	public void mostrarProductosPorNombre(String nombre) {
		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			String hql = "SELECT p FROM Productos p WHERE p.nombre = :nombre";
			java.util.List listaProductos = session.createQuery(hql).setParameter("nombre", nombre).list();
			Iterator iterator = listaProductos.iterator();

			while (iterator.hasNext()) {
				Productos p = (Productos) iterator.next();

				System.out.println("ID: " + p.getId());
				System.out.println("Nombre: " + p.getNombre());
				System.out.println("Precio: " + p.getPrecio());

			}
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();
		}
	}

	public void ordenarProductosPorPrecio() {

		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			java.util.List listaProductos = session.createQuery("FROM Productos ORDER BY precio").list();
			Iterator iterator = listaProductos.iterator();

			while (iterator.hasNext()) {
				Productos p = (Productos) iterator.next();

				System.out.println("ID: " + p.getId());
				System.out.println("Nombre: " + p.getNombre());
				System.out.println("Precio: " + p.getPrecio());

			}
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();
		}

	}

	public void mostrarPrecioDe(String nombre) {

		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			String hql = "SELECT p FROM Productos p WHERE p.nombre = :nombre";
			java.util.List listaProductos = session.createQuery(hql).setParameter("nombre", nombre).list();
			Iterator iterator = listaProductos.iterator();

			while (iterator.hasNext()) {
				Productos p = (Productos) iterator.next();

				System.out.println("Precio: " + p.getPrecio());

			}
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();
		}

	}

	public void mostrarProductoPorId(int id) {

		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			Productos producto = (Productos) session.createQuery("FROM Productos WHERE id = :id").setParameter("id", id)
					.uniqueResult();

			if (producto != null) {

				System.out.println("ID: " + producto.getId());
				System.out.println("Nombre: " + producto.getNombre());
				System.out.println("Precio: " + producto.getPrecio());

			} else {
				System.out.println("No se encontró ningún producto con la ID: " + id);
			}

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();
		}

	}
}// class
