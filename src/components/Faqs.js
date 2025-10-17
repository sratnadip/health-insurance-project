import React, { useEffect, useState } from "react";
import "./Faqs.css";

export default function FAQs() {
  const [faqs, setFaqs] = useState([]);
  const [openIndex, setOpenIndex] = useState(null); 
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchFaqs = async () => {
      try {
        const response = await fetch("http://localhost:8089/faq/all");
        if (!response.ok) throw new Error("Failed to fetch FAQs");
        const data = await response.json();
        setFaqs(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchFaqs();
  }, []);

  const toggleFAQ = (index) => {
    setOpenIndex(openIndex === index ? null : index); 
  };

  if (loading) return <div className="faq-container">Loading FAQs...</div>;
  if (error) return <div className="faq-container error">Error: {error}</div>;

  return (
    <div className="faq-container">
      <h2 className="faq-heading">Frequently Asked Questions</h2>
      {faqs.map((faq, index) => (
        <div key={faq.id} className="faq-card">
          <div
            className="faq-question"
            onClick={() => toggleFAQ(index)}
          >
            <span className="faq-number">{index + 1}.</span>{" "}
            {faq.question}
            <span className="faq-icon">{openIndex === index ? "−" : "+"}</span>
          </div>
          {openIndex === index && (
            <div
  className={`faq-answer ${openIndex === index ? "open" : ""}`}
>
  {faq.answer}
</div>

          )}
        </div>
      ))}
    </div>
  );
}
