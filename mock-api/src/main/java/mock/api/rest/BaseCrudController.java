package mock.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.log4j.Log4j2;
import mock.api.model.BaseEntity;

@Log4j2
@ResponseStatus(value = HttpStatus.OK)
public abstract class BaseCrudController<Entity extends BaseEntity, ID extends Object, Repository extends CrudRepository<Entity, ID>> {

	@Autowired
	protected Repository repository;

	@GetMapping("/")
	public @ResponseBody Iterable<Entity> getAll() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			log.error("getAll", e);
			throw e;
		}
	}

	@GetMapping("/{id}")
	public @ResponseBody Entity getOne(@PathVariable ID id) {
		return (Entity) repository.findById(id).get();
	}

	@PostMapping("/")
	public @ResponseBody Entity create(@RequestBody Entity newEntity) {
		return repository.save(newEntity);
	}

	protected abstract void updateImpl(Entity entity, Entity newEntity);

	@PutMapping("/{id}")
	public @ResponseBody Entity update(@RequestBody Entity newEntity, @PathVariable ID id) {
		return repository.findById(id).map(entity -> {
			updateImpl(entity, newEntity);
			return repository.save(entity);
		}).orElseGet(() -> {
			throw new RuntimeException();
		});
	}

	@DeleteMapping("/{id}")
	public @ResponseBody void delete(@PathVariable ID id) {
		repository.deleteById(id);
	}
}