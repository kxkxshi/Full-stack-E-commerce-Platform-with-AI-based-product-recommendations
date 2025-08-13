import React, { useEffect, useState } from 'react'

const API = 'http://localhost:8080/api'

function ProductCard({p, onAdd}){
  return (
    <div style={{border:'1px solid #ddd', borderRadius:12, padding:12}}>
      <img src={p.imageUrl} alt={p.name} style={{width:'100%', height:160, objectFit:'cover', borderRadius:8}}/>
      <h3>{p.name}</h3>
      <p style={{opacity:.7, margin:0}}>{p.brand} · {p.category}</p>
      <strong>₹ {p.price}</strong>
      <div style={{display:'flex', gap:8, marginTop:8}}>
        <button onClick={()=>onAdd(p)}>Add to cart</button>
        <a href={'#/product/'+p.id}>View</a>
      </div>
    </div>
  )
}

function useProducts(){
  const [data, setData] = useState([])
  useEffect(()=>{
    fetch(API + '/products').then(r=>r.json()).then(setData)
  },[])
  return data
}

function Recommendations({productId}){
  const [data, setData] = useState([])
  useEffect(()=>{
    fetch(API + '/recommendations/' + productId + '?limit=4').then(r=>r.json()).then(setData)
  },[productId])
  if(!data.length) return null
  return (
    <div>
      <h4>Recommended</h4>
      <div style={{display:'grid', gridTemplateColumns:'repeat(auto-fill, minmax(220px,1fr))', gap:12}}>
        {data.map(p=> <ProductCard key={p.id} p={p} onAdd={()=>{}} />)}
      </div>
    </div>
  )
}

function ProductPage({id, onAdd}){
  const [p, setP] = useState(null)
  useEffect(()=>{
    fetch(API + '/products/' + id).then(r=>r.json()).then(setP)
  },[id])
  if(!p) return <p>Loading...</p>
  return (
    <div>
      <a href="#/">← Back</a>
      <div style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:24}}>
        <img src={p.imageUrl} alt={p.name} style={{width:'100%', borderRadius:12}}/>
        <div>
          <h2>{p.name}</h2>
          <p>{p.description}</p>
          <p><b>Brand:</b> {p.brand} · <b>Category:</b> {p.category}</p>
          <h3>₹ {p.price}</h3>
          <button onClick={()=>onAdd(p)}>Add to cart</button>
        </div>
      </div>
      <div style={{marginTop:24}}>
        <Recommendations productId={p.id} />
      </div>
    </div>
  )
}

function Cart({items, onCheckout}){
  const total = items.reduce((s,i)=> s + (i.price * i.quantity), 0)
  return (
    <div>
      <h3>Cart</h3>
      {!items.length && <p>No items yet.</p>}
      {items.map(i => (
        <div key={i.id} style={{display:'flex', justifyContent:'space-between'}}>
          <span>{i.name} × {i.quantity}</span>
          <b>₹ {i.price * i.quantity}</b>
        </div>
      ))}
      <hr/>
      <div style={{display:'flex', justifyContent:'space-between'}}>
        <b>Total</b><b>₹ {total}</b>
      </div>
      <button disabled={!items.length} onClick={onCheckout} style={{marginTop:12}}>Checkout</button>
    </div>
  )
}

export default function App(){
  const [route, setRoute] = useState(window.location.hash || '#/')
  const [cart, setCart] = useState([])

  useEffect(()=>{
    const onHash = () => setRoute(window.location.hash || '#/')
    window.addEventListener('hashchange', onHash)
    return () => window.removeEventListener('hashchange', onHash)
  }, [])

  const products = useProducts()

  const handleAdd = (p) => {
    setCart(prev => {
      const ex = prev.find(x => x.id === p.id)
      if(ex) return prev.map(x => x.id === p.id ? {...x, quantity: x.quantity + 1} : x)
      return [...prev, {id:p.id, name:p.name, price:p.price, quantity:1}]
    })
    fetch(API + '/cart', {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({productId:p.id, quantity:1})})
  }

  const checkout = async () => {
    await fetch(API + '/orders/checkout', {method:'POST'})
    setCart([])
    alert('Order placed! (Demo)')
  }

  let content = null
  if(route.startsWith('#/product/')){
    const id = route.split('/').pop()
    content = <ProductPage id={id} onAdd={handleAdd}/>
  } else {
    content = (
      <div>
        <div style={{display:'grid', gridTemplateColumns:'3fr 1fr', gap:24}}>
          <div>
            <h2>Products</h2>
            <div style={{display:'grid', gridTemplateColumns:'repeat(auto-fill, minmax(220px,1fr))', gap:12}}>
              {products.map(p => <ProductCard key={p.id} p={p} onAdd={handleAdd}/>)}
            </div>
          </div>
          <Cart items={cart} onCheckout={checkout}/>
        </div>
      </div>
    )
  }

  return (
    <div style={{maxWidth:1100, margin:'0 auto', padding:24}}>
      <header style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
        <a href="#/"><h1>Ecom AI</h1></a>
        <a href="https://github.com" target="_blank">Github</a>
      </header>
      {content}
      <footer style={{marginTop:48, opacity:.6}}>
        <small>Semester demo – upgrade auth/DB/recommender for production.</small>
      </footer>
    </div>
  )
}
