select * from lotes L 
left join (select COUNT(id_apartacao) as MAX, id_lote 
from apartacoes GROUP BY id_lote) A 
on L.id_lote = A.id_lote left join alimento R on L.id_alimento = R.id_alimento;  


select * from lotes L left join apartacoes A on L.id_lote = A.id_lote left join alimento R on L.id_alimento = R.id_alimento order by L.id_lote;